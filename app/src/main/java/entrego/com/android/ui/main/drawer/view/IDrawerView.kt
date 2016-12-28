package entrego.com.android.ui.main.drawer.view

import entrego.com.android.storage.model.CustomerModel
import entrego.com.android.storage.model.EntregoRouteModel

/**
 * Created by bertalt on 12.12.16.
 */
interface IDrawerView {

    fun showEmptyView()
    fun showDeliveryView()
    fun buildMultiDelivery()
    fun setupHeader(customer: CustomerModel?)
    fun setupRoute(route: EntregoRouteModel?)

}