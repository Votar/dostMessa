package entrego.com.entrego.ui.main.home.view

import android.content.Context
import entrego.com.entrego.location.diraction.Route
import entrego.com.entrego.storage.model.CustomerModel
import entrego.com.entrego.storage.model.DeliveryModel
import entrego.com.entrego.storage.model.EntregoRouteModel

/**
 * Created by bertalt on 05.12.16.
 */
interface IHomeView {

    fun moveCamera(latitude: Double, longitude: Double)
    fun prepareNoDelivery()
    fun prepareRoute(route: EntregoRouteModel, customer: CustomerModel)
    fun buildRoute(route: Route)
    fun getFragmentContext(): Context
    fun showMessage(message: String)

}