package entrego.com.android.web.model.response.profile

import entrego.com.android.storage.model.UserVehicleModel
import entrego.com.android.web.model.response.EntregoResult


class EntregoResultGetVehicle( code: Int?,
                               message: String?,
                              val payload: UserVehicleModel) : EntregoResult(code, message) {

}