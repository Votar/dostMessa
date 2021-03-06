package com.entregoya.msngr.ui.main.delivery.description.cancel.presenter

import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import com.entregoya.msngr.R
import com.entregoya.msngr.binding.Delivery
import com.entregoya.msngr.storage.preferences.EntregoStorage
import com.entregoya.msngr.ui.main.delivery.description.cancel.ReasonsAdapter
import com.entregoya.msngr.ui.main.delivery.description.cancel.model.CancelDelivery
import com.entregoya.msngr.ui.main.delivery.description.cancel.view.ICancelDeliveryView

class CancelDeliveryPresenter : ICancelDeliveryPresenter {
    override fun setupRecyclerView(recycler: RecyclerView, reasons: List<String>) {
        recycler.adapter = ReasonsAdapter(reasons, mReasonClickedListener)
    }

    var mView: ICancelDeliveryView? = null
    override fun onCreate(view: ICancelDeliveryView) {
        mView = view
    }

    override fun onDestroy() {
        mView = null
    }

    val mReasonClickedListener = object : ReasonsAdapter.OnReasonClicked {
        override fun onClickedReason(reason: String) {
            val ctx = mView?.getActivityContext()
            if (ctx != null)
                cancelDeliveryDialog(ctx, reason)
        }
    }

    fun cancelDeliveryDialog(context: Context, reason: String) {

        val builder = AlertDialog.Builder(context)
        builder.setMessage(R.string.cancel_delivery_message)
        builder.setPositiveButton(android.R.string.ok, { dialogInterface: DialogInterface, _: Int ->
            val token = EntregoStorage.getToken()
            mView?.onShowProgress()

            val deliveryId = Delivery.getInstance().id

            CancelDelivery.executeAsync(token,deliveryId,reason, object : CancelDelivery.CancelDeliveryListener {
                override fun onSuccessCancel() {
                    mView?.onHideProgress()
                    mView?.showSuccessScreen()
                }

                override fun onFailureCancel(code: Int?, message: String?) {
                    mView?.onHideProgress()
                    mView?.showMessage(message)
                }
            })
        })
        builder.setNegativeButton(android.R.string.cancel, { dialogInterface: DialogInterface, _: Int ->
            dialogInterface.dismiss()
        })
        builder.show()
    }


}