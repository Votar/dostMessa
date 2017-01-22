package entrego.com.android.ui.account.history.presenter

import entrego.com.android.storage.model.DeliveryModel
import entrego.com.android.ui.account.history.model.DeliveryHistoryModel
import entrego.com.android.ui.account.history.view.IDeliveryHistoryView

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