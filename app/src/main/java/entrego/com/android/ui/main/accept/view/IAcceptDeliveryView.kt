package entrego.com.android.ui.main.accept.view

import android.content.Context

interface IAcceptDeliveryView {
    fun showProgress()
    fun hideProgress()
    fun hideAcceptFragment()
    fun getToken():String
    fun showMessage(message:String?)
    fun showMessage(stringId:Int)

}