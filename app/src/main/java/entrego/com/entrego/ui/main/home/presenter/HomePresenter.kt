package entrego.com.entrego.ui.main.home.presenter

import android.text.TextUtils
import entrego.com.entrego.R
import entrego.com.entrego.location.diraction.DirectionFinder
import entrego.com.entrego.location.diraction.DirectionFinderListener
import entrego.com.entrego.location.diraction.Route
import entrego.com.entrego.storage.model.DeliveryModel
import entrego.com.entrego.storage.preferences.EntregoStorage
import entrego.com.entrego.ui.main.home.model.DeliveryRequest
import entrego.com.entrego.ui.main.home.view.IHomeView
import entrego.com.entrego.util.Logger
import entrego.com.entrego.util.toDirectionFormat
import entrego.com.entrego.web.api.EntregoApi

/**
 * Created by bertalt on 06.12.16.
 */
class HomePresenter(val view: IHomeView) : IHomePresenter {


    val getDeliveryListener = object : DeliveryRequest.ResultGetDelivery {

        override fun onSuccessGetDelivery(delivery: DeliveryModel?) {

            if (delivery != null) {

                DirectionFinder(getDiractionListener,
                        delivery.route.start.toDirectionFormat(),
                        delivery.route.destination.toDirectionFormat(),
                        view.getFragmentContext().getString(R.string.google_maps_key))
                        .execute()

            } else {
                view.prepareNoDelivery()
            }
        }

        override fun onFailureGetDelivery(code: Int?, message: String?) {

            view.prepareNoDelivery()

            when (code) {
                null -> view.showMessage("")
            }
        }
    }
    val getDiractionListener = object : DirectionFinderListener {

        override fun onDirectionFinderStart() {

        }

        override fun onDirectionFinderSuccess(route: MutableList<Route>?) {

            Logger.logd("Received route!!")


            if (route != null) {
                view.buildRoute(route[0])
            }
        }

    }

    override fun onCreate() {

        val token = EntregoStorage(view.getFragmentContext()).getToken()
        if (!token.isEmpty()) {
            DeliveryRequest.requestDelivery(token, getDeliveryListener)
        }


    }

    override fun onDestroy() {

    }

}