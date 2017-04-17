package com.entregoya.msngr.storage.model

class UserVehicleModel(val brand: String = "",
                       val model: String = "",
                       val year: Int = 0,
                       val cylinders: Int = 0,
                       val plate: String = "") {


    override fun toString(): String {
        return "UserVehicleModel(brand='$brand', model='$model', year=$year, cylinders=$cylinders, plate='$plate')"
    }
}