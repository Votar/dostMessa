package entrego.com.entrego.ui.main.home.presenter

import android.text.TextUtils
import entrego.com.entrego.storage.model.DeliveryModel
import entrego.com.entrego.storage.preferences.EntregoStorage
import entrego.com.entrego.ui.main.home.model.DeliveryRequest
import entrego.com.entrego.ui.main.home.view.IHomeView
import entrego.com.entrego.web.api.EntregoApi

/**
 * Created by bertalt on 06.12.16.
 */
class HomePresenter(val view: IHomeView) : IHomePresenter {

    val getDeliveryListener = object : DeliveryRequest.ResultGetDelivery {

        override fun onSuccessGetDelivery(delivery: DeliveryModel?) {

            if (delivery != null) {
                view.prepareDelivery(delivery)
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

    override fun onCreate() {

        val token = EntregoStorage(view.getFragmentContext()).getToken()
        if (!token.isEmpty()) {
            DeliveryRequest.requestDelivery(token, getDeliveryListener)
        }


    }

    override fun onDestroy() {

    }

}