package com.entregoya.msngr.ui.incomes.history.details.presenter

import com.entregoya.msngr.ui.incomes.history.details.view.IDetailsOfServiceView

/**
 * Created by bertalt on 23.12.16.
 */
interface IDetailsOfServicePresenter {
    fun onCreate(view : IDetailsOfServiceView)
    fun onDestroy()
}