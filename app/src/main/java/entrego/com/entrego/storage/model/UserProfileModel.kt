package entrego.com.entrego.storage.model

/**
 * Created by bertalt on 30.11.16.
 */
class UserProfileModel(val email: String,
                       val name: String,
                       val phone: EntregoPhoneModel) {

    override fun toString(): String {
        return "UserProfileModel(email='$email', name='$name', phone=$phone)"
    }
}