package com.entregoya.msngr.ui.account.vehicle.edit.presenter

/**
 * Created by bertalt on 02.12.16.
 */

interface IEditVehiclePresenter {

    fun updateVehicle(brand: String,
                      model: String,
                      year: Int,
                      cylinders: Int,
                      plate: String)



}