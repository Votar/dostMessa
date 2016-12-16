package entrego.com.android.ui.account.history.presenter

import entrego.com.android.ui.account.history.view.IDeliveryHistoryView

/**
 * Created by bertalt on 16.12.16.
 */
interface IDeliveryHistoryPresenter {
    fun onCreate(view: IDeliveryHistoryView)
    fun onDestroy()
    fun requestHistoryList(token:String)

}