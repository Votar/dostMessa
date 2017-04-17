package com.entregoya.msngr.ui.main.drawer.presenter

import android.databinding.Observable
import android.support.v7.widget.SwitchCompat
import com.entregoya.msngr.BR
import com.entregoya.msngr.binding.Delivery
import com.entregoya.msngr.binding.HistoryHolder
import com.entregoya.msngr.storage.model.DeliveryModel
import com.entregoya.msngr.storage.model.PointStatus
import com.entregoya.msngr.storage.preferences.EntregoStorage
import com.entregoya.msngr.ui.main.drawer.model.DrawerModel
import com.entregoya.msngr.ui.main.drawer.view.IDrawerView
import com.entregoya.msngr.ui.main.home.model.DeliveryRequest
import com.entregoya.msngr.web.api.ApiContract

class DrawerPresenter : IDrawerPresenter {

    var mView: IDrawerView? = null
    var mToken: String = ""
    val mDeliveryChangedListener = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(p0: Observable?, p1: Int) {

            when (p1) {
                BR.instance -> onBuildView()
            }
        }
    }

    override fun onStart(view: IDrawerView, token: String) {
        this.mView = view
        this.mToken = token
        Delivery.getInstance().addOnPropertyChangedCallback(mDeliveryChangedListener)
        onBuildView()
    }

    fun onBuildView() {
        mView?.refreshView()
    }

    override fun buildSwitchListByState(history: HistoryHolder, switchList: List<SwitchCompat>) {

        for (next in switchList)
            next.isEnabled = false

        when (history.getCurrentPoint().status) {
            PointStatus.PENDING -> {
                switchList[0].isEnabled = true
                switchList[0].isChecked = false

            }
            PointStatus.GOING -> {
                switchList[1].isEnabled = true
                switchList[1].isChecked = false
            }
            PointStatus.WAITING, PointStatus.DONE -> {
                when (history.getDestinationPoint().status) {
                    PointStatus.PENDING -> {
                        switchList[2].isEnabled = true
                        switchList[2].isChecked = false
                    }
                    PointStatus.GOING -> {
                        switchList[3].isEnabled = true
                        switchList[3].isChecked = false
                    }
                    PointStatus.WAITING -> {
                        switchList[4].isEnabled = true
                        switchList[4].isChecked = false
                    }
                    PointStatus.DONE -> {
                    }
                    else -> {
                    }
                }
            }
            else -> {
            }
        }
    }

    override fun changeStatus(switch: SwitchCompat, status: PointStatus) {

        val changeStatusListener = object : DrawerModel.ChangeStatusListener {
            override fun onSuccessChange(updatedDelivery: DeliveryModel) {
                Delivery.getInstance().update(updatedDelivery)
                onBuildView()
            }

            override fun onFailureChange(code: Int?, message: String?) {
                switch.isChecked = false
                switch.isEnabled = true
                when (code) {
                    8, ApiContract.INCORRECT_ORDER_STATE -> updateDelivery()
                    else -> mView?.showMessage(message)
                }
            }
        }
        val deliveryId = Delivery.getInstance().id
        DrawerModel.nextStatus(mToken, deliveryId, status, changeStatusListener)
    }

    override fun onStop() {
        Delivery.getInstance().removeOnPropertyChangedCallback(mDeliveryChangedListener)
        mView = null
    }

    override fun updateDelivery() {
        val token = EntregoStorage.getToken()
        DeliveryRequest.updateDelivery(token, null)
    }
}