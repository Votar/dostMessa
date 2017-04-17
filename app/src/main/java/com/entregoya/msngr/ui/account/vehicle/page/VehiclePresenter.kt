package com.entregoya.msngr.ui.account.vehicle.page

import com.entregoya.msngr.mvp.presenter.BaseMvpPresenter
import com.entregoya.msngr.storage.model.UserVehicleModel
import com.entregoya.msngr.ui.account.vehicle.page.model.GetVehicleRequest

class VehiclePresenter : BaseMvpPresenter<VehicleContract.View>(),
        VehicleContract.Presenter {

    override fun requestModel(token: String) {
        mView?.showProgress()
        GetVehicleRequest().requestAsync(token, mGetVehicleListener)
    }

    val mGetVehicleListener = object : GetVehicleRequest.ResponseListener {
        override fun onFailureResponse(code: Int?, message: String?) {
            mView?.hideProgress()
            mView?.showError(message)
        }

        override fun onSuccessResponse(model: UserVehicleModel) {
            mView?.hideProgress()
            mView?.setupView(model)
        }

    }
}