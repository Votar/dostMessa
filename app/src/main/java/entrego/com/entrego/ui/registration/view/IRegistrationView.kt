package entrego.com.entrego.ui.registration.view

import android.content.Context

/**
 * Created by bertalt on 29.11.16.
 */
interface IRegistrationView {
    fun setErrorEmail(message: String)
    fun setErrorPassword(message: String)
    fun setErrorConfPassword()
    fun showMessage(message:String)
    fun showProgress()
    fun hideProgress()
    fun successRegistration()
}