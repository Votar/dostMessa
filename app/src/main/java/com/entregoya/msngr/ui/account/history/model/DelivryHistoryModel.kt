package com.entregoya.msngr.ui.account.history.model

import com.entregoya.msngr.storage.model.DeliveryModel
import com.entregoya.msngr.web.api.ApiCreator
import com.entregoya.msngr.web.api.EntregoApi
import com.entregoya.msngr.web.model.response.delivery.EntregoResultHistoryDelivery
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

object DeliveryHistoryRequest {

    const val GET_DELIVERY_HISTORY = "messenger/statistics/orders"

    interface GetDeliveryHistoryApi {
        @Headers(EntregoApi.CONTENT_JSON)
        @GET(GET_DELIVERY_HISTORY)
        fun send(@Header(EntregoApi.TOKEN) token: String): Call<EntregoResultHistoryDelivery>
    }

    interface GetDeliveryHistoryListener {
        fun onSuccessGetDeliveryHistory(resultArray: Array<DeliveryModel>)
        fun onFailureGetDeliveryHistory(code: Int?, message: String?)
    }

    val mRequest = ApiCreator.server.create(GetDeliveryHistoryApi::class.java)

    fun requestDeliveryHistory(token: String, listener: GetDeliveryHistoryListener?) {
        mRequest.send(token)
                .enqueue(object : Callback<EntregoResultHistoryDelivery> {
                    override fun onResponse(call: Call<EntregoResultHistoryDelivery>?, response: Response<EntregoResultHistoryDelivery>?) {
                        if (response != null)
                            when (response.body().code) {
                                0 -> listener?.onSuccessGetDeliveryHistory(response.body().payload)
                                else -> listener?.onFailureGetDeliveryHistory(response.body().code, response.body().message)
                            }
                    }

                    override fun onFailure(call: Call<EntregoResultHistoryDelivery>?, t: Throwable?) {
                        listener?.onFailureGetDeliveryHistory(null, null)
                    }
                })
    }
}