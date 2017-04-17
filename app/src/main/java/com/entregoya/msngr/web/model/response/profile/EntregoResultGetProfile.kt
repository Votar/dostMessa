package com.entregoya.msngr.web.model.response.profile

import com.entregoya.msngr.storage.model.UserProfileModel
import com.entregoya.msngr.web.model.response.EntregoResult

/**
 * Created by bertalt on 30.11.16.
 */
class EntregoResultGetProfile( code: Int?,
                               message: String?,
                              val payload: UserProfileModel) : EntregoResult(code, message) {

}