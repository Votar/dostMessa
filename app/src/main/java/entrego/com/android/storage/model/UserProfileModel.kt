package entrego.com.android.storage.model

/**
 * Created by bertalt on 30.11.16.
 */
class UserProfileModel(val email: String,
                       val name: String,
                       val phone: EntregoPhoneModel) {
    var userPicUrl: String = ""

    constructor(email: String,
                name: String,
                phone: EntregoPhoneModel,
                userPicUrl: String) : this(email, name, phone) {
        this.userPicUrl = userPicUrl
    }


    override fun toString(): String {
        return "UserProfileModel(email='$email', name='$name', phone=$phone)"
    }
}