package com.entregoya.msngr.ui.main.drawer.shopping

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.util.Base64
import com.entregoya.msngr.R
import com.entregoya.msngr.mvp.presenter.BaseMvpPresenter
import com.entregoya.msngr.storage.preferences.EntregoStorage
import com.entregoya.msngr.ui.main.drawer.model.SendShoppingReceiptRequest
import com.entregoya.msngr.web.api.ApiContract
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import java.io.ByteArrayOutputStream
import java.util.*

class ShoppingPresenter : BaseMvpPresenter<ShoppingContract.View>(),
        ShoppingContract.Presenter {


    companion object {
        const val RQT_CAMERA = 0x211
    }

    var mPermissionListener: PermissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
            mView?.getActivityContext()?.startActivityForResult(cameraIntent, RQT_CAMERA)
        }

        override fun onPermissionDenied(deniedPermissions: ArrayList<String>) {
            mView?.showError(R.string.error_permission_denied)
        }
    }
    val mToken = EntregoStorage.getToken()
    var mReceiptPhoto: Bitmap? = null
    var mOrderId: Long? = null
    val mSendShoppingResponseListener = object : SendShoppingReceiptRequest.SendShoppingReceiptRequestListener {
        override fun onSuccessSendShoppingReceiptRequest() {
            mView?.hideProgress()
            mView?.showMessage(R.string.success_send_receipt)
        }

        override fun onFailureSendShoppingReceiptRequest(code: Int?, message: String?) {
            mView?.hideProgress()
            when (code) {
                ApiContract.RESPONSE_INVALID_TOKEN -> mView?.onLogout()
                else -> mView?.showError(message)
            }
        }

    }

    override fun requestPhoto() {
        TedPermission(mView?.getActivityContext())
                .setPermissionListener(mPermissionListener)
                .setDeniedMessage(R.string.text_permission_required)
                .setPermissions(Manifest.permission.CAMERA)//set permission
                .check()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && data != null)
            if (requestCode == RQT_CAMERA) {
                val photo = data.extras?.get("data") as? Bitmap
                if (photo != null) {
                    mView?.setupPhotoHolder(photo)
                    mReceiptPhoto = photo
                } else {
                    mView?.setupDefaultHolder()
                    mReceiptPhoto = null
                }
            }
    }

    override fun sendReceipt(amount: String) {
        if (mOrderId == null) throw IllegalStateException("No orderId in presenter")
        if (mReceiptPhoto == null)
            mView?.showError(R.string.error_no_receipt_photo)
        else {
            try {
                val amountFloat = java.lang.Float.valueOf(amount)
                val baos = ByteArrayOutputStream()
                mReceiptPhoto?.compress(Bitmap.CompressFormat.JPEG, 50, baos) //bm is the bitmap object
                val byteArray = baos.toByteArray()
                val photoEncoded = Base64.encodeToString(byteArray, Base64.DEFAULT)
                mView?.showProgress()
                SendShoppingReceiptRequest().executeAsync(mToken, mOrderId!!,
                        amountFloat,
                        photoEncoded,
                        mSendShoppingResponseListener)

            } catch (ex: NumberFormatException) {
                return
            }
        }
    }

    override fun setupOrderId(orderId: Long) {
        mOrderId = orderId
    }
}