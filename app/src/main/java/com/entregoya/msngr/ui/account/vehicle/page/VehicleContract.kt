package com.entregoya.msngr.ui.account.vehicle.page

import com.entregoya.msngr.mvp.presenter.IBaseMvpPresenter
import com.entregoya.msngr.mvp.view.IBaseMvpView
import com.entregoya.msngr.storage.model.UserVehicleModel


object VehicleContract {
    interface View : IBaseMvpView {
        fun showProgress()
        fun hideProgress()
        fun setupView(vehicle: UserVehicleModel)
    }

    interface Presenter : IBaseMvpPresenter<View> {
        fun requestModel(token: String)
    }

}