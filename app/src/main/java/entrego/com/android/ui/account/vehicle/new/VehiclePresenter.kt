package entrego.com.android.ui.account.vehicle.new

import entrego.com.android.mvp.presenter.BaseMvpPresenter
import entrego.com.android.storage.model.UserVehicleModel
import entrego.com.android.ui.account.vehicle.new.model.GetVehicleRequest

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