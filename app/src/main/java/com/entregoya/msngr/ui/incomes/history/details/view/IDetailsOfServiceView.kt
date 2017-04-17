package com.entregoya.msngr.ui.incomes.history.details.view

/**
 * Created by bertalt on 23.12.16.
 */
interface IDetailsOfServiceView {
    fun onShowView()
    fun onShowMessage(message:String)
    fun onShowProgress()
    fun onHideProgress()
}