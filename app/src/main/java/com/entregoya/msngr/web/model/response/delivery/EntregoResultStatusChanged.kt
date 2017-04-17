package com.entregoya.msngr.web.model.response.delivery

import com.entregoya.msngr.storage.model.DeliveryModel
import com.entregoya.msngr.storage.model.UserProfileModel
import com.entregoya.msngr.web.model.response.EntregoResult

/**
 * Created by Admin on 14.01.2017.
 */
class EntregoResultStatusChanged( code: Int?,
                                   message: String?,
                                   val payload: DeliveryModel) : EntregoResult(code, message){
}