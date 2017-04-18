package com.entregoya.msngr.ui.account.help.reports.create

import com.entregoya.msngr.mvp.presenter.IBaseMvpPresenter
import com.entregoya.msngr.mvp.view.IBaseMvpView


interface SendReportContract {

    interface View : IBaseMvpView {
        fun showProgress()
        fun hideProgress()
    }

    interface Presenter : IBaseMvpPresenter<View> {
        fun sendReport(text: String)
    }
}