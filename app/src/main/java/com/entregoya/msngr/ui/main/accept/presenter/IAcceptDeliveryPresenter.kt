package com.entregoya.msngr.ui.main.accept.presenter

import android.view.View
import com.entregoya.msngr.ui.main.accept.view.IAcceptDeliveryView

/**
 * Created by bertalt on 10.01.17.
 */
interface IAcceptDeliveryPresenter {
    fun onStart(view: IAcceptDeliveryView)
    fun onStop()
    fun acceptDelivery(id:Long)
    fun declineDelivery(id:Long)
}

