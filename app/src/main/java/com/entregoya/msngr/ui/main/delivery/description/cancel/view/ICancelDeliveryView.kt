package com.entregoya.msngr.ui.main.delivery.description.cancel.view

import android.content.Context

/**
 * Created by bertalt on 20.12.16.
 */
interface ICancelDeliveryView {
    fun onShowProgress()
    fun onHideProgress()
    fun showMessage(message: String?)
    fun getActivityContext(): Context
    fun showSuccessScreen()
}