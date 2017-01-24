package entrego.com.android.ui.auth.view

import android.app.ProgressDialog
import android.content.Context

/**
 * Created by bertalt on 29.11.16.
 */
interface IAuthView {
    fun getContext() : Context
    fun setErrorEmail(stringId: Int)
    fun setErrorPassword(stringId: Int)
    fun showMessage(message:String)
    fun showProgress()
    fun hideProgress()
    fun goToRegistration()
    fun goToMainScreen()
    fun successRestorePassword()
}