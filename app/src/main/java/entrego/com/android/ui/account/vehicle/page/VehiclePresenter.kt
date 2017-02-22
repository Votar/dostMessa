package entrego.com.android.ui.account.vehicle.page

import entrego.com.android.mvp.presenter.BaseMvpPresenter
import entrego.com.android.storage.model.UserVehicleModel
import entrego.com.android.ui.account.vehicle.page.model.GetVehicleRequest

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