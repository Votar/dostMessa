package entrego.com.android.ui.main.drawer.presenter

import android.databinding.Observable
import android.support.v7.widget.SwitchCompat
import entrego.com.android.BR
import entrego.com.android.binding.Delivery
import entrego.com.android.storage.model.DeliveryModel
import entrego.com.android.storage.model.EntregoRouteModel
import entrego.com.android.storage.model.PointStatus
import entrego.com.android.ui.main.drawer.model.DrawerModel
import entrego.com.android.ui.main.drawer.view.IDrawerView
import entrego.com.android.ui.main.home.model.DeliveryRequest
import entrego.com.android.util.Logger


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

    override fun buildSwitchListByState(route: EntregoRouteModel, switchList: List<SwitchCompat>) {

        for (next in switchList)
            next.isEnabled = false

        when (route.getCurrentPoint().status) {
            PointStatus.PENDING -> {
                switchList[0].isEnabled = true
            }
            PointStatus.GOING -> {
                switchList[1].isEnabled = true
                switchList[1].isChecked = false
            }
            PointStatus.WAITING, PointStatus.DONE -> {
                when (route.getDestinationPoint().status) {
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
                    PointStatus.DONE -> mView?.signBill()
                    else -> {
                    }
                }
            };
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
                mView?.showMessage(message)
                when(code){
                    8->DeliveryRequest.requestDelivery(null)
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
}