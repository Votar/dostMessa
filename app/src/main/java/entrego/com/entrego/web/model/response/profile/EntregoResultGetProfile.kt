package entrego.com.entrego.web.model.response.profile

import entrego.com.entrego.storage.model.UserProfileModel
import entrego.com.entrego.web.model.response.EntregoResult

/**
 * Created by bertalt on 30.11.16.
 */
class EntregoResultGetProfile( code: Int?,
                               message: String?,
                              val payload: UserProfileModel) : EntregoResult(code, message) {

}