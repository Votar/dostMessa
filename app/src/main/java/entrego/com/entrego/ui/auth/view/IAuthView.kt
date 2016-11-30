package entrego.com.entrego.ui.auth.view

import android.app.ProgressDialog
import android.content.Context

/**
 * Created by bertalt on 29.11.16.
 */
interface IAuthView {
    fun getContext() : Context
    fun setErrorEmail(message: String)
    fun setErrorPassword(message: String)
    fun showMessage(message:String)
    fun showProgress()
    fun hideProgress()
    fun goToRegistration()
    fun goToMainScreen()
    fun successRestorePassword()
}