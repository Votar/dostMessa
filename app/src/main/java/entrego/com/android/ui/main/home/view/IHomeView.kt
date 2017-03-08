package entrego.com.android.ui.main.home.view

import android.content.Context
import entrego.com.android.location.diraction.Route
import entrego.com.android.storage.model.CustomerModel
import entrego.com.android.storage.model.DeliveryModel
import entrego.com.android.storage.model.EntregoRouteModel

interface IHomeView {

    fun moveCamera(latitude: Double, longitude: Double)
    fun prepareNoDelivery()
    fun prepareRoute(route: EntregoRouteModel)
    fun buildPath(path: String)
    fun showAcceptFragment()
    fun sendDeliveryReceivedNotification(sum : String)
    fun dissmissAcceptFragment()
    fun getFragmentContext(): Context
    fun showProgress()
    fun hideProgress()
    fun showMessage(idString: Int)
    fun showMessage(message:String?)
    fun showAlertNoGoogleMaps()
    fun errorInSendOffline()
}