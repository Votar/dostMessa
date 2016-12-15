package entrego.com.android.web.model.response.profile

import entrego.com.android.storage.model.UserProfileModel
import entrego.com.android.web.model.response.EntregoResult

/**
 * Created by bertalt on 30.11.16.
 */
class EntregoResultGetProfile( code: Int?,
                               message: String?,
                              val payload: UserProfileModel) : EntregoResult(code, message) {

}