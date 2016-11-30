package entrego.com.entrego.web.model.request.registration

import entrego.com.entrego.storage.model.EntregoPhoneModel

/**
 * Created by bertalt on 29.11.16.
 */
class RegistrationBody(val email: String,
                       val name: String,
                       val password: String,
                       val phone: EntregoPhoneModel) {
}