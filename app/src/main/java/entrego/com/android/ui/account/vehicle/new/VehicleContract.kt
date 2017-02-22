package entrego.com.android.ui.account.vehicle.new

import entrego.com.android.mvp.presenter.IBaseMvpPresenter
import entrego.com.android.mvp.view.IBaseMvpView
import entrego.com.android.storage.model.UserVehicleModel


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