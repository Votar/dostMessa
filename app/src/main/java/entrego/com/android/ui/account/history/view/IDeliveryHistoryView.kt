package entrego.com.android.ui.account.history.view

import android.content.Context

/**
 * Created by bertalt on 16.12.16.
 */
interface IDeliveryHistoryView {
    fun showEmptyView()
    fun showHistoryList()
    fun showProgress()
    fun hideProgress()
    fun showMessage(message:String?)
    fun getAppContext(): Context?
}