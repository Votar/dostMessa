package entrego.com.android.ui.account.help.reports.view

import entrego.com.android.ui.account.help.reports.model.ReportModel

/**
 * Created by bertalt on 26.12.16.
 */
interface IReportsListView {
    fun buildView(reports: List<ReportModel>)
    fun showEmptyView()
    fun showMessage(message:String?)
}