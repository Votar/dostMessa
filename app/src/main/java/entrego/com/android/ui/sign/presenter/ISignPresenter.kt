package entrego.com.android.ui.sign.presenter

import entrego.com.android.ui.sign.view.ISignView

/**
 * Created by bertalt on 13.12.16.
 */
interface ISignPresenter {
    fun onCreate(view : ISignView)
    fun onDestroy()
    fun sendSign(token:String)
}