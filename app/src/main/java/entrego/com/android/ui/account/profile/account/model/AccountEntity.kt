package entrego.com.android.ui.account.profile.account.model

/**
 * Created by bertalt on 26.12.16.
 */
data class AccountEntity(
        val bankName: String,
        val fullName: String,
        val accountNumber: String,
        val swiftCode: String) {
}