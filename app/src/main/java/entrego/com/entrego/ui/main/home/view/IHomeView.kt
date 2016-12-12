package entrego.com.entrego.ui.main.home.view

import android.content.Context
import entrego.com.entrego.location.diraction.Route
import entrego.com.entrego.storage.model.DeliveryModel

/**
 * Created by bertalt on 05.12.16.
 */
interface IHomeView {

    fun moveCamera(latitude: Double, longitude: Double)
    fun prepareNoDelivery()
    fun prepareDelivery()
    fun buildRoute(route: Route)
    fun getFragmentContext() :Context
    fun showMessage(message:String)

}