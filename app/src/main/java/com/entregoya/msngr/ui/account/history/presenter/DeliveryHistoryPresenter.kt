package com.entregoya.msngr.ui.account.history.presenter

import com.entregoya.msngr.storage.model.DeliveryModel
import com.entregoya.msngr.ui.account.history.model.DeliveryHistoryModel
import com.entregoya.msngr.ui.account.history.view.IDeliveryHistoryView

class DeliveryHistoryPresenter : IDeliveryHistoryPresenter {


    var view: IDeliveryHistoryView? = null
    override fun onCreate(view: IDeliveryHistoryView) {
        this.view = view
    }

    override fun onDestroy() {
        view = null
    }

    override fun requestHistoryList(token: String) {

        DeliveryHistoryModel.requestDeliveryHistory(token, mGetDeliveryHistoryListener)
    }


    val mGetDeliveryHistoryListener = object : DeliveryHistoryModel.GetDeliveryHistoryListener {
        override fun onSuccessGetDeliveryHistory(resultArray: Array<DeliveryModel>) {
            if (resultArray.count() > 0)
                view?.showHistoryList(resultArray)
            else
                view?.showEmptyView()
        }

        override fun onFailureGetDeliveryHistory(message: String?) {
            view?.showMessage(message)
            view?.showEmptyView()

        }

    }
}