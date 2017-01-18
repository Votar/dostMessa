package entrego.com.android.ui.main.home.view

import android.content.Context
import entrego.com.android.location.diraction.Route
import entrego.com.android.storage.model.CustomerModel
import entrego.com.android.storage.model.DeliveryModel
import entrego.com.android.storage.model.EntregoRouteModel

/**
 * Created by bertalt on 05.12.16.
 */
interface IHomeView {

    fun moveCamera(latitude: Double, longitude: Double)
    fun prepareNoDelivery()
    fun prepareRoute(route: EntregoRouteModel)
    fun buildPath(path: String)
    fun showAcceptFragment()
    fun dissmissAcceptFragment()
    fun getFragmentContext(): Context

}