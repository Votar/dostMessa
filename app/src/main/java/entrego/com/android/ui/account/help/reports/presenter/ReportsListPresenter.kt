package entrego.com.android.ui.account.help.reports.presenter

import entrego.com.android.ui.account.help.reports.model.ReportModel
import entrego.com.android.ui.account.help.reports.model.ReportsController
import entrego.com.android.ui.account.help.reports.view.IReportsListView

/**
 * Created by bertalt on 26.12.16.
 */
class ReportsListPresenter : IReportsListPresenter {
    var mView: IReportsListView? = null
    val getReportsListener = object : ReportsController.GetReportsListener {
        override fun onSuccessGet(reports: List<ReportModel>) {
            mView?.buildView(reports)
        }

        override fun onFailureGet(code: Int?, message: String?) {
            mView?.showEmptyView()
            mView?.showMessage(message)
        }

    }

    override fun onCreate(view: IReportsListView) {
        mView = view
        ReportsController.getReportsAsync(getReportsListener)
    }

    override fun onDestroy() {
        mView = null
    }
}