package entrego.com.entrego.ui.main.account.vehicle.edit.presenter

import entrego.com.entrego.R
import entrego.com.entrego.storage.model.UserVehicleModel
import entrego.com.entrego.storage.preferences.EntregoStorage
import entrego.com.entrego.ui.main.account.vehicle.edit.model.UserVehicle
import entrego.com.entrego.ui.main.account.vehicle.edit.view.IEditVehicleView
import entrego.com.entrego.web.model.response.common.FieldErrorResponse

/**
 * Created by bertalt on 02.12.16.
 */
class EditVehiclePresenter(val view: IEditVehicleView) : IEditVehiclePresenter {

    companion object FIELDS {
        const val BRAND = "brand"
        const val PLATE = "palte"
        const val MODEL = "model"
    }

    override fun updateVehicle(brand: String, model: String, year: Int, cylinders: Int, plate: String) {

        if (brand.isEmpty() || model.isEmpty() || plate.isEmpty()) {
            view.showMessage(view.getContext().getString(R.string.error_empty_fields))

        } else {
            val token = EntregoStorage(view.getContext()).getToken()
            val vehicle = UserVehicleModel(brand, model, year, cylinders, plate)

            view.showProgress()
            UserVehicle.update(token, vehicle,
                    object : UserVehicle.ResultUpdateListener {
                        override fun onFieldValidatorError(field: FieldErrorResponse) {

                            view.hideProgress()

                            when (field.field) {
                                BRAND -> view.setErrorBrand(field.message)
                                PLATE -> view.setErrorPlate(field.message)
                                MODEL -> view.setErrorModel(field.message)
                                else -> view.showMessage(field.field + " " + field.message)
                            }

                        }

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