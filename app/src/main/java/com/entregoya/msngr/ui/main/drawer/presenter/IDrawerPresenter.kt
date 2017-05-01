package com.entregoya.msngr.ui.main.drawer.presenter

import android.support.v7.widget.SwitchCompat
import com.entregoya.msngr.binding.HistoryHolder
import com.entregoya.msngr.storage.model.PointStatus
import com.entregoya.msngr.ui.main.drawer.view.IDrawerView

interface IDrawerPresenter {
    fun onStart(view: IDrawerView, token: String)
    fun onStop()
    fun changeStatus(switch: SwitchCompat, status: PointStatus)
    fun buildSwitchListByState(history: HistoryHolder, switchList: List<SwitchCompat>)
    fun updateDelivery()
    fun sendShoppingReceipt()
}