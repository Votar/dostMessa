package entrego.com.android.ui.main.drawer.presenter

import android.support.v7.widget.SwitchCompat
import entrego.com.android.binding.HistoryHolder
import entrego.com.android.storage.model.PointStatus
import entrego.com.android.ui.main.drawer.view.IDrawerView

interface IDrawerPresenter {
    fun onStart(view: IDrawerView, token: String)
    fun onStop()
    fun changeStatus(switch: SwitchCompat, status: PointStatus)
    fun buildSwitchListByState(history: HistoryHolder, switchList: List<SwitchCompat>)
    fun updateDelivery()
}