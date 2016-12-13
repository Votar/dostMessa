package entrego.com.entrego.ui.sign.presenter

import entrego.com.entrego.ui.sign.view.ISignView

/**
 * Created by bertalt on 13.12.16.
 */
interface ISignPresenter {
    fun onCreate(view : ISignView)
    fun onDestroy()
    fun sendSign(token:String)
}