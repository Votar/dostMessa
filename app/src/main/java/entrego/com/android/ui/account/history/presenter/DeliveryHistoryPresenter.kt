package entrego.com.android.ui.account.history.presenter

import entrego.com.android.storage.model.DeliveryModel
import entrego.com.android.ui.account.history.model.DeliveryHistoryModel
import entrego.com.android.ui.account.history.view.IDeliveryHistoryView

/**
 * Created by bertalt on 16.12.16.
 */
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
            view?.showHistoryList(resultArray)
        }

        override fun onFailureGetDeliveryHistory(message: String?) {
            view?.showMessage(message)
            view?.showEmptyView()

        }

    }
}