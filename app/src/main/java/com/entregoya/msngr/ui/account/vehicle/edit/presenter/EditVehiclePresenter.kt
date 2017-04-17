package com.entregoya.msngr.ui.account.vehicle.edit.presenter

import com.entregoya.msngr.R
import com.entregoya.msngr.storage.model.UserVehicleModel
import com.entregoya.msngr.storage.preferences.EntregoStorage
import com.entregoya.msngr.ui.account.vehicle.edit.model.UserVehicle
import com.entregoya.msngr.ui.account.vehicle.edit.view.IEditVehicleView
import com.entregoya.msngr.web.model.response.common.FieldErrorResponse


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
            val token = EntregoStorage.getToken()
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
                            EntregoStorage.setUserVehicle(vehicle)
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