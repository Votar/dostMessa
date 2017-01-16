package entrego.com.android.ui.account.profile.account.model

import android.os.Handler

/**
 * Created by bertalt on 26.12.16.
 */
object AccountController {

    var account = AccountEntity("Bank of America", "Silvester Stalonom", "345623-53-100", "34567656754343546")

    interface UpdateAccountListener {
        fun onAccountUpdated(accountEntity: AccountEntity)
        fun onAccountFailureUpdate(code: Int, message: String?)
        fun onFieldError(fieldName:String, message :String?)
    }

    fun getActualData(): AccountEntity {
        //TODO:Remove mocks
        return account
    }

    fun requestToUpdate(bankName: String, fullName: String, accountNumber: String, swiftCode: String, listener: UpdateAccountListener?) {

        Handler().postDelayed({
            account = AccountEntity(bankName, fullName, accountNumber, swiftCode)
            listener?.onAccountUpdated(account)
        }, 2000)
    }
}