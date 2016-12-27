package entrego.com.android.ui.account.help.reports.presenter

import entrego.com.android.ui.account.help.reports.view.IReportsListView

/**
 * Created by bertalt on 26.12.16.
 */
interface IReportsListPresenter {
    fun onCreate(view: IReportsListView)
    fun onDestroy()
}