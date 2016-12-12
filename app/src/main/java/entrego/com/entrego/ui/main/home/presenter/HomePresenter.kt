package entrego.com.entrego.ui.main.home.presenter

import android.content.Context
import android.location.Geocoder
import android.os.AsyncTask
import android.os.Handler
import com.google.android.gms.maps.model.LatLng
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
class HomePresenter(var view: IHomeView?) : IHomePresenter {


    val getDeliveryListener = object : DeliveryRequest.ResultGetDelivery {

        override fun onSuccessGetDelivery() {

            if (view != null) {
                val delivery = DeliveryInstance.getInstance()
                if (delivery != null) {

                    view!!.prepareDelivery()
                    requestDirection(delivery.route)

                } else {
                    view!!.prepareNoDelivery()
                }
            }
        }

        override fun onFailureGetDelivery(code: Int?, message: String?) {

            view?.prepareNoDelivery()

            when (code) {

                2 -> EventBus.getDefault().post(LogoutEvent())
                null -> view?.showMessage("")
            }
        }
    }
    val getDirectionListener = object : DirectionFinderListener {

        override fun onDirectionFinderStart() {

        }

        override fun onDirectionFinderSuccess(route: MutableList<Route>?) {

            Logger.logd("Received route!!")


            if (route != null) {
                DeliveryInstance.getInstance().path = route[0]
                view?.buildRoute(route[0])
            }
        }

    }

    override fun onCreate() {

        val delivery = DeliveryInstance.getInstance()
        if (delivery == null) {
            if (view != null) {
                val token = EntregoStorage(view!!.getFragmentContext()).getToken()
                if (!token.isEmpty()) {
                    DeliveryRequest.requestDelivery(token, getDeliveryListener)
                }
            }
        } else {
            view?.prepareDelivery()
            if (delivery.path == null)
                requestDirection(delivery.route)
            else
                view?.buildRoute(delivery.path)
        }
    }

    fun requestDirection(route: EntregoRouteModel) {
        DirectionFinder(getDirectionListener,
                route.start.toDirectionFormat(),
                route.destination.toDirectionFormat(),
                view!!.getFragmentContext().getString(R.string.google_maps_key))
                .execute()
    }

    override fun onDestroy() {

        view = null
    }
}