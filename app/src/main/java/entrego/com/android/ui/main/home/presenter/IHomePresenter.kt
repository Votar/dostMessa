package entrego.com.android.ui.main.home.presenter

import entrego.com.android.ui.main.home.view.IHomeView

interface IHomePresenter {
    fun onStart(view: IHomeView)
    fun onStop()
    fun sendOffline(token:String)
    fun noGoogleMapsException()
    fun onBuildView()
}