package com.entregoya.msngr.ui.main.accept.presenter

import com.entregoya.msngr.R
import com.entregoya.msngr.binding.Delivery
import com.entregoya.msngr.storage.preferences.EntregoStorage
import com.entregoya.msngr.ui.main.accept.model.DeliveryInteractor
import com.entregoya.msngr.ui.main.accept.view.IAcceptDeliveryView
import com.entregoya.msngr.ui.main.home.model.DeliveryRequest
import com.entregoya.msngr.web.api.ApiContract
import com.entregoya.msngr.web.api.ApiCreator

class AcceptDeliveryPresenter : IAcceptDeliveryPresenter {
    var mView: IAcceptDeliveryView? = null
    override fun onStart(view: IAcceptDeliveryView) {
        mView = view
    }

    override fun onStop() {
        mView = null
    }

    override fun acceptDelivery(id: Long) {
        val token = EntregoStorage.getToken()
        DeliveryInteractor.accept(token, id, mDeliveryAcceptListener)
    }

    override fun declineDelivery(id: Long) {
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
            when(code){
                ApiContract.INCORRECT_ORDER_STATE->{
                    DeliveryRequest.requestDelivery(null)
                }
                else-> mView?.showMessage(message)

            }
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
            when(code){
                ApiContract.INCORRECT_ORDER_STATE->{
                    DeliveryRequest.requestDelivery(null)
                }
                else-> mView?.showMessage(message)

            }
        }
    }
}