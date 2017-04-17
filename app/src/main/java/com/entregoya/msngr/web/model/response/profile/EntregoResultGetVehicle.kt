package com.entregoya.msngr.web.model.response.profile

import com.entregoya.msngr.storage.model.UserVehicleModel
import com.entregoya.msngr.web.model.response.EntregoResult


class EntregoResultGetVehicle( code: Int?,
                               message: String?,
                              val payload: UserVehicleModel) : EntregoResult(code, message) {

}