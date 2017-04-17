package com.entregoya.msngr.ui.main.home.view

import android.content.Context
import com.entregoya.msngr.binding.HistoryHolder

interface IHomeView {

    fun moveCamera(latitude: Double, longitude: Double)
    fun prepareNoDelivery()
    fun prepareRoute(history: HistoryHolder)
    fun buildPath(path: String)
    fun showAcceptFragment()
    fun sendDeliveryReceivedNotification()
    fun dismissAcceptFragment()
    fun getFragmentContext(): Context
    fun showProgress()
    fun hideProgress()
    fun showMessage(idString: Int)
    fun showMessage(message:String?)
    fun showAlertNoGoogleMaps()
    fun errorInSendOffline()
}