package entrego.com.android.ui.registration.view

import android.content.Context

/**
 * Created by bertalt on 29.11.16.
 */
interface IRegistrationView {
    fun setErrorName(message:String)
    fun setErrorEmail(message: String)
    fun setErrorPassword(message: String)
    fun setErrorPhoneCode(message:String)
    fun setErrorPhoneNumber(message:String)
    fun setErrorConfPassword()
    fun showMessage(message:String)
    fun showProgress()
    fun hideProgress()
    fun successRegistration()
}