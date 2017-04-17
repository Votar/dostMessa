package com.entregoya.msngr.ui.main.drawer.sign.presenter

import android.graphics.Bitmap
import com.entregoya.msngr.ui.main.drawer.sign.view.ISignView

/**
 * Created by bertalt on 13.12.16.
 */
interface ISignPresenter {
    fun onCreate(view : ISignView)
    fun onDestroy()
    fun sendSign(token:String, signBill: ByteArray)
}