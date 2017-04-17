package com.entregoya.msngr.ui.main.delivery.description.cancel.model

import android.os.Handler
import android.os.Looper
import android.support.annotation.Nullable
import com.google.gson.JsonElement
import com.entregoya.msngr.binding.Delivery
import com.entregoya.msngr.ui.main.accept.model.DeliveryInteractor
import com.entregoya.msngr.util.Logger
import com.entregoya.msngr.web.api.ApiContract
import com.entregoya.msngr.web.api.ApiCreator
import com.entregoya.msngr.web.api.EntregoApi
import com.entregoya.msngr.web.model.response.EntregoResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object CancelDelivery {

    interface CancelDeliveryListener {
        fun onSuccessCancel()
        fun onFailureCancel(code: Int?, message: String?)
    }

    var isRequested: Boolean = false

    fun executeAsync(token: String, deliveryId: Long, reason: String, @Nullable listener: CancelDelivery.CancelDeliveryListener?) {

        if (isRequested)
            return

        isRequested = true

        ApiCreator.server
                .create(EntregoApi.CancelDelivery::class.java)
                .cancelDelivery(token, deliveryId)
                .enqueue(object : Callback<EntregoResult> {
                    override fun onFailure(call: Call<EntregoResult>?, t: Throwable?) {
                        listener?.onFailureCancel(null, null)
                        isRequested = false
                    }

                    override fun onResponse(call: Call<EntregoResult>?, response: Response<EntregoResult>?) {
                        if (response?.body() != null) {
                            when (response.body().code) {
                                ApiContract.RESPONSE_OK -> {
                                    listener?.onSuccessCancel()
                                    Delivery.getInstance().update(null)
                                }
                                else -> listener?.onFailureCancel(
                                        response.body().code,
                                        response.body().message
                                )
                            }
                        }
                        isRequested = false
                    }
                })
    }
}