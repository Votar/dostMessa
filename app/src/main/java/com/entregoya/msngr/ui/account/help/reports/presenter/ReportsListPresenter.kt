package com.entregoya.msngr.ui.account.help.reports.presenter

import com.entregoya.msngr.storage.preferences.EntregoStorage
import com.entregoya.msngr.ui.account.help.reports.model.ReportEntity
import com.entregoya.msngr.ui.account.help.reports.model.ReportListRequest
import com.entregoya.msngr.ui.account.help.reports.view.IReportsListView


class ReportsListPresenter : IReportsListPresenter {
    var mView: IReportsListView? = null
    val mToken = EntregoStorage.getToken()
    val mReportListListener = object : ReportListRequest.ReportListRequestListener {
        override fun onSuccessReportListRequest(resultList: Array<ReportEntity>) {
            if (resultList.isNotEmpty())
                mView?.buildView(resultList.toList())
            else
                mView?.showEmptyView()
        }

        override fun onFailureReportListRequest(code: Int?, message: String?) {
            mView?.showEmptyView()
            mView?.showMessage(message)
        }
    }

    override fun onCreate(view: IReportsListView) {
        mView = view
        requestList()
    }

    override fun onDestroy() {
        mView = null
    }

    fun requestList() {
        ReportListRequest().executeAsync(mToken, mReportListListener)
    }
}