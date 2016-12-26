package entrego.com.android.ui.account.profile.account.view

import entrego.com.android.ui.account.profile.account.model.AccountModel

/**
 * Created by bertalt on 26.12.16.
 */
interface IAccountEditView {
    fun showProgress()
    fun hideProgress()
    fun showMessage(message: String?)
    fun prepareView(account: AccountModel)
    fun showFieldError(fieldsName:String, message:String?)
}