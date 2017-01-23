package entrego.com.android.ui.main.home.presenter

import entrego.com.android.ui.main.home.view.IHomeView

/**
 * Created by bertalt on 06.12.16.
 */
interface IHomePresenter {
    fun onStart(view: IHomeView)
    fun onStop()
    fun sendOffline(token:String)



}