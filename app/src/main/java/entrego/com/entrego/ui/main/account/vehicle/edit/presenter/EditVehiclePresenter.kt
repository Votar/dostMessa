package entrego.com.entrego.ui.main.account.vehicle.edit.presenter

import entrego.com.entrego.R
import entrego.com.entrego.storage.model.UserVehicleModel
import entrego.com.entrego.storage.preferences.EntregoStorage
import entrego.com.entrego.ui.main.account.vehicle.edit.model.UserVehicle
import entrego.com.entrego.ui.main.account.vehicle.edit.view.IEditVehicleView

/**
 * Created by bertalt on 02.12.16.
 */
class EditVehiclePresenter(val view: IEditVehicleView) : IEditVehiclePresenter {


    override fun updateVehicle(brand: String, model: String, year: Int, cylinders: Int, plate: String) {

        if (brand.isEmpty() || model.isEmpty() || plate.isEmpty()) {
            view.showMessage(view.getContext().getString(R.string.error_empty_fields))

        } else {
            val token = EntregoStorage(view.getContext()).getToken()
            val vehicle = UserVehicleModel(brand, model, year, cylinders, plate)

            view.showProgress()
            UserVehicle.update(token, vehicle,
                    object : UserVehicle.ResultUpdateListener {
                        override fun onSuccessUpdate(userVehicle: UserVehicleModel) {
                            view.hideProgress()
                            EntregoStorage(view.getContext()).setUserVehicle(vehicle)
                            view.onSuccessUpdate()
                        }

                        override fun onFailureUpdate(message: String) {
                            view.hideProgress()
                            view.showMessage(message)
                        }

                    })
        }


    }
}