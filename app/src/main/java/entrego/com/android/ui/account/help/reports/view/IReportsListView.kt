package entrego.com.android.ui.account.help.reports.view

import entrego.com.android.ui.account.help.reports.model.ReportEntity

/**
 * Created by bertalt on 26.12.16.
 */
interface IReportsListView {
    fun buildView(reports: List<ReportEntity>)
    fun showEmptyView()
    fun showMessage(message:String?)
}