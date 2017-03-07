package entrego.com.android.ui.main.accept.presenter

import entrego.com.android.R
import entrego.com.android.binding.Delivery
import entrego.com.android.storage.preferences.EntregoStorage
import entrego.com.android.ui.main.accept.model.DeliveryInteractor
import entrego.com.android.ui.main.accept.view.IAcceptDeliveryView

class AcceptDeliveryPresenter : IAcceptDeliveryPresenter {
    var mView: IAcceptDeliveryView? = null
    override fun onStart(view: IAcceptDeliveryView) {
        mView = view
    }

    override fun onStop() {
        mView = null
    }

    override fun acceptDelivery(id: Int) {
        val token = EntregoStorage.getToken()
        DeliveryInteractor.accept(token, id, mDeliveryAcceptListener)
    }

    override fun declineDelivery(id: Int) {
        val token = EntregoStorage.getToken()

        DeliveryInteractor.decline(token, id, mDeliveryDeclineListener)
    }


    val mDeliveryAcceptListener = object : DeliveryInteractor.ResultListener {
        override fun onSuccess() {
            mView?.hideProgress()
            mView?.hideAcceptFragment()
            mView?.showMessage(R.string.accepted)
        }

        override fun onFailure(message: String?, code: Int?) {
            mView?.hideProgress()
            mView?.showMessage(message)
        }
    }

    val mDeliveryDeclineListener = object : DeliveryInteractor.ResultListener {
        override fun onSuccess() {
            mView?.hideProgress()
            mView?.showMessage(R.string.declined)
            mView?.hideAcceptFragment()
            Delivery.getInstance().update(null)
        }

        override fun onFailure(message: String?, code: Int?) {
            mView?.hideProgress()
            mView?.showMessage(message)
        }
    }
}