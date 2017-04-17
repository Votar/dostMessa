package com.entregoya.msngr.ui.account.history.presenter

import com.entregoya.msngr.ui.account.history.view.IDeliveryHistoryView

/**
 * Created by bertalt on 16.12.16.
 */
interface IDeliveryHistoryPresenter {
    fun onCreate(view: IDeliveryHistoryView)
    fun onDestroy()
    fun requestHistoryList(token:String)

}