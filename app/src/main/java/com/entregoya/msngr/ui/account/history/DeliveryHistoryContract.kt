package com.entregoya.msngr.ui.account.history

import com.entregoya.msngr.mvp.presenter.IBaseMvpPresenter
import com.entregoya.msngr.mvp.view.IBaseMvpView
import com.entregoya.msngr.storage.model.DeliveryModel


interface DeliveryHistoryContract {
    interface View : IBaseMvpView {
        fun showProgress()
        fun hideProgress()
        fun showEmptyView()
        fun showHistoryList(resultList: Array<DeliveryModel>)
    }

    interface Presenter : IBaseMvpPresenter<View> {
        fun requestHistoryList()
    }
}