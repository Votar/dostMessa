package entrego.com.android.storage.model

class UserProfileModel(val email: String,
                       val name: String,
                       val phone: EntregoPhoneModel) {
    val userPicUrl: String
    get() = "https://en.opensuse.org/images/0/0b/Icon-user.png"


    override fun toString(): String {
        return "UserProfileModel(email='$email', name='$name', phone=$phone)"
    }
}