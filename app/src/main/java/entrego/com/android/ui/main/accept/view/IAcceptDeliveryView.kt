package entrego.com.android.ui.main.accept.view

import android.content.Context

/**
 * Created by bertalt on 10.01.17.
 */
interface IAcceptDeliveryView {
    fun showProgress()
    fun hideProgress()
    fun hideAcceptFragment()
    fun getToken():String
    fun showMessage(stringId:Int)
}