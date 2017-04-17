package com.entregoya.msngr.ui.incomes.history.details.model

import android.os.Handler

/**
 * Created by bertalt on 23.12.16.
 */
object DetailOfServiceModel {

    interface DetailsResponseListener {
        fun onSuccessResponseDetails()
        fun onFailureResponseDetails()
        fun onStartedRequest()
    }
    fun request(listener: DetailsResponseListener){
        listener.onStartedRequest()

        Handler().postDelayed({ listener.onSuccessResponseDetails() },2000)
    }
}