package com.entregoya.msngr.ui.main.home.presenter

import com.entregoya.msngr.ui.main.home.view.IHomeView

interface IHomePresenter {
    fun onStart(view: IHomeView)
    fun onStop()
    fun sendOffline(token:String)
    fun noGoogleMapsException()
    fun onBuildView()
}