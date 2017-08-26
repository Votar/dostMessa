package com.entregoya.msngr.ui.main.delivery.description.presenter

import com.entregoya.msngr.ui.main.delivery.description.view.IDescreptionView

interface IDescriptionPresenter {
    fun onStart(view: IDescreptionView)
    fun onStop()
    fun callCustomer()
}