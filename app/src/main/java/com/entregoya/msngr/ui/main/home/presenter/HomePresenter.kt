package com.entregoya.msngr.ui.main.home.presenter

import android.databinding.Observable
import android.text.TextUtils
import com.entregoya.msngr.BR
import com.entregoya.msngr.R
import com.entregoya.msngr.binding.Delivery
import com.entregoya.msngr.storage.model.OrderStatus
import com.entregoya.msngr.ui.main.home.model.OfflineRequest
import com.entregoya.msngr.ui.main.home.view.IHomeView
import com.entregoya.msngr.util.event_bus.LogoutEvent
import com.entregoya.msngr.web.model.response.CommonResponseListener
import org.greenrobot.eventbus.EventBus
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE


class HomePresenter : IHomePresenter {


    var view: IHomeView? = null
    val mDeliveryChangedListener = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(p0: Observable?, p1: Int) {
            when (p1) {
                BR.instance -> {
                    onBuildView()
                }
            }
        }
    }

    override fun onStart(view: IHomeView) {
        this.view = view
        Delivery.getInstance().addOnPropertyChangedCallback(mDeliveryChangedListener)
        onBuildView()
    }

    override fun onStop() {
        Delivery.getInstance().removeOnPropertyChangedCallback(mDeliveryChangedListener)
        view = null
    }

    override fun onBuildView() {
        val delivery = Delivery.getInstance()

        if (delivery.status.equals(OrderStatus.PENDING.value, true)) {
            view?.showAcceptFragment()
//            view?.sendDeliveryReceivedNotification()
        } else
            view?.dismissAcceptFragment()

        if (delivery.history != null) {
            view?.prepareRoute(delivery.history)
            if (delivery.path.line.isNotEmpty())
                view?.buildPath(delivery.path.line)

        } else {
            view?.prepareNoDelivery()
            view?.getFragmentContext()?.apply {
                (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).cancelAll()
            }
        }
    }


    override fun sendOffline(token: String) {
        OfflineRequest.request(token, object : CommonResponseListener {
            override fun onSuccessResponse() {
            }

            override fun onFailureResponse(code: Int?, message: String?) {
                view?.errorInSendOffline()
                when (code) {
                    2 -> EventBus.getDefault().post(LogoutEvent())
                    else -> view?.showMessage(message)
                }
            }

        })
    }

    override fun noGoogleMapsException() {
        view?.showAlertNoGoogleMaps()
    }

}