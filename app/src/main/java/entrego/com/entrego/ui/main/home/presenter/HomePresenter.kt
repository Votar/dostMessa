package entrego.com.entrego.ui.main.home.presenter

import android.content.Context
import android.databinding.Observable
import android.location.Geocoder
import android.os.AsyncTask
import android.os.Handler
import com.google.android.gms.maps.model.LatLng
import entrego.com.entrego.BR
import entrego.com.entrego.R
import entrego.com.entrego.location.diraction.DirectionFinder
import entrego.com.entrego.location.diraction.DirectionFinderListener
import entrego.com.entrego.location.diraction.Route
import entrego.com.entrego.storage.model.DeliveryModel
import entrego.com.entrego.storage.model.EntregoRouteModel
import entrego.com.entrego.storage.model.binding.DeliveryInstance
import entrego.com.entrego.storage.preferences.EntregoStorage
import entrego.com.entrego.ui.main.home.model.DeliveryRequest
import entrego.com.entrego.ui.main.home.view.IHomeView
import entrego.com.entrego.util.Logger
import entrego.com.entrego.util.event_bus.LogoutEvent
import entrego.com.entrego.util.toDirectionFormat
import org.greenrobot.eventbus.EventBus

/**
 * Created by bertalt on 06.12.16.
 */
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
        onBuildView()
        DeliveryInstance.getInstance().addOnPropertyChangedCallback(mDeliveryChangedListener)

    }


    fun onBuildView() {

        val delivery = DeliveryInstance.getInstance()

        if (delivery.route != null && delivery.customer != null) {
            view?.prepareRoute(delivery.route, delivery.customer)

            if (delivery.path != null)
                view?.buildRoute(delivery.path)

        } else {
            view?.prepareNoDelivery()
        }
    }

    override fun onStop() {

        DeliveryInstance.getInstance().removeOnPropertyChangedCallback(mDeliveryChangedListener)
        view = null
    }
}