package com.entregoya.msngr.ui.main.delivery.description.presenter

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import com.entregoya.msngr.R
import com.entregoya.msngr.binding.Delivery
import com.entregoya.msngr.ui.main.delivery.description.view.IDescreptionView
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import java.util.*

class DescriptionPresenter : IDescriptionPresenter {
    override fun callCustomer() {
        val phone = Delivery.getInstance()?.customer?.phone
        if (phone == null)
            view?.showMessage(R.string.no_customer_phone)
        else {
            view?.getViewContext()?.let {
                try {
                    val intent = Intent(Intent.ACTION_CALL)
                    intent.data = Uri.parse("tel:" + phone.toView())
                    view?.getViewContext()?.startActivity(intent)
                } catch (ex: SecurityException) {
                    TedPermission(it)
                            .setPermissionListener(mCallPermissionListener)
                            .setPermissions(Manifest.permission.CALL_PHONE)
                            .check()
                }
            }
        }
    }

    var view: IDescreptionView? = null
    var geocodeWorker: AsyncTask<Void, Void, Boolean>? = null

    override fun onStart(view: IDescreptionView) {
        this.view = view
    }

    override fun onStop() {
        geocodeWorker?.cancel(true)
        view = null
    }

    val mCallPermissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            callCustomer()
        }

        override fun onPermissionDenied(deniedPermissions: ArrayList<String>?) {
            view?.showMessage(R.string.text_permission_required)
        }
    }
}