package entrego.com.android.web.model.response.delivery

import entrego.com.android.storage.model.DeliveryModel
import entrego.com.android.storage.model.UserProfileModel
import entrego.com.android.web.model.response.EntregoResult

/**
 * Created by Admin on 14.01.2017.
 */
class EntregoResultStatusChanged( code: Int?,
                                   message: String?,
                                   val payload: DeliveryModel) : EntregoResult(code, message){
}