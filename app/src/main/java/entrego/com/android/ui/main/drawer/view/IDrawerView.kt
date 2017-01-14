package entrego.com.android.ui.main.drawer.view

import android.content.Context
import android.support.v7.widget.SwitchCompat
import android.view.View
import entrego.com.android.storage.model.CustomerModel
import entrego.com.android.storage.model.EntregoRouteModel

/**
 * Created by bertalt on 12.12.16.
 */
interface IDrawerView {

    fun refreshView()
    fun buildMultiDelivery()
    fun signBill()
    fun showMessage(message:String?)
}