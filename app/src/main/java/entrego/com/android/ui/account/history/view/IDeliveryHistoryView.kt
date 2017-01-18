package entrego.com.android.ui.account.history.view

import android.content.Context
import entrego.com.android.storage.model.DeliveryModel

/**
 * Created by bertalt on 16.12.16.
 */
interface IDeliveryHistoryView {
    fun showEmptyView()
    fun showHistoryList(resultList:Array<DeliveryModel>)
    fun showMessage(message:String?)
    fun getAppContext(): Context?
}