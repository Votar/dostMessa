package entrego.com.entrego.ui.main.drawer.view

import entrego.com.entrego.storage.model.CustomerModel
import entrego.com.entrego.storage.model.EntregoRouteModel

/**
 * Created by bertalt on 12.12.16.
 */
interface IDrawerView {

    fun showEmptyView()
    fun showDeliveryView()
    fun setupHeader(customer: CustomerModel?)
    fun setupRoute(route: EntregoRouteModel?)

}