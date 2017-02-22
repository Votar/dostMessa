package entrego.com.android.ui.main.home.presenter

import android.databinding.Observable
import android.text.TextUtils
import entrego.com.android.BR
import entrego.com.android.R
import entrego.com.android.binding.Delivery
import entrego.com.android.storage.model.OrderStatus
import entrego.com.android.ui.main.home.model.OfflineRequest
import entrego.com.android.ui.main.home.view.IHomeView
import entrego.com.android.util.event_bus.LogoutEvent
import entrego.com.android.web.model.response.CommonResponseListener
import org.greenrobot.eventbus.EventBus

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

        if (delivery.status.equals(OrderStatus.PENDING.value, true))
            view?.showAcceptFragment()
        else
            view?.dissmissAcceptFragment()

        if (delivery.route != null) {
            view?.prepareRoute(delivery.route)
            if (delivery.route.path.line.isNotEmpty())
                view?.buildPath(delivery.route.path.line)

        } else
            view?.prepareNoDelivery()

    }


    override fun sendOffline(token: String) {
        view?.showProgress()
        OfflineRequest.request(token, object : CommonResponseListener {
            override fun onSuccessResponse() {
                view?.hideProgress()
            }

            override fun onFailureResponse(code: Int?, message: String?) {
                view?.hideProgress()
                when (code) {
                    2 -> EventBus.getDefault().post(LogoutEvent())
                    else -> view?.showMessage(R.string.er_default_network_error)
                }
            }

        })
    }

    override fun noGoogleMapsException() {
        view?.showAlertNoGoogleMaps()
    }

}