package entrego.com.android.ui.main.delivery.description.cancel.presenter

import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import entrego.com.android.R
import entrego.com.android.binding.Delivery
import entrego.com.android.storage.preferences.EntregoStorage
import entrego.com.android.ui.main.delivery.description.cancel.ReasonsAdapter
import entrego.com.android.ui.main.delivery.description.cancel.model.CancelDelivery
import entrego.com.android.ui.main.delivery.description.cancel.view.ICancelDeliveryView

/**
 * Created by bertalt on 20.12.16.
 */
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
        builder.setPositiveButton(android.R.string.ok, { dialogInterface: DialogInterface, i: Int ->
            val token = EntregoStorage.getToken()
            mView?.onShowProgress()

            val deliveryId = Delivery.getInstance().id

            CancelDelivery.executeAsync(token,deliveryId,reason, object : CancelDelivery.CancelDeliveryListener {
                override fun onSuccessCancel() {
                    mView?.onHideProgress()
                    mView?.showSuccessScreen()
                }

                override fun onFailureCancel(message: String?, code:Int?) {
                    mView?.onHideProgress()
                    mView?.showMessage(message)
                }
            })
        })
        builder.setNegativeButton(android.R.string.cancel, { dialogInterface: DialogInterface, i: Int ->
            dialogInterface.dismiss()
        })
        builder.show()
    }


}