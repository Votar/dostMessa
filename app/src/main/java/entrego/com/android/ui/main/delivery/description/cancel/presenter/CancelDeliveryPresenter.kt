package entrego.com.android.ui.main.delivery.description.cancel.presenter

import android.support.v7.widget.RecyclerView
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
            if (ctx != null) {
                val token = EntregoStorage(ctx).getToken()
                mView?.onShowProgress()
                CancelDelivery.executeAsync(token, reason, object : CancelDelivery.CancelDeliveryListener {
                    override fun onSuccessCancel() {
                        mView?.onHideProgress()
                        mView?.showMessage("GC")
                        mView?.onReturnToRoot()
                    }

                    override fun onFailureCancel(message: String) {
                        mView?.onHideProgress()
                        mView?.showMessage(message)
                    }
                })

            }
        }

    }

}