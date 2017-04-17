package com.entregoya.msngr.ui.incomes.history.model

import com.entregoya.msngr.storage.model.DeliveryModel
import com.entregoya.msngr.web.api.ApiCreator
import com.entregoya.msngr.web.api.EntregoApi
import com.entregoya.msngr.web.model.response.statistic.EntregoHistoryServiceDetails
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path

/**
 * Created by Admin on 22.01.2017.
 */
object HistoryServiceDetailsModel {
    const val END_POINT = "messenger/statistics/order/{id}"

    interface API {
        @Headers(EntregoApi.CONTENT_JSON)
        @GET(END_POINT)
        fun send(@Header(EntregoApi.TOKEN) token: String, @Path("id") id: Int): Call<EntregoHistoryServiceDetails>
    }

    private val mRequest = ApiCreator.server.create(API::class.java)

    interface GetHistoryServicesDetailsListener {
        fun onSuccessGetHistory(result: DeliveryModel)
        fun onFailureGetHistory(code: Int?, message: String?)
    }

    fun request(token: String, id: Int, listener: GetHistoryServicesDetailsListener?) {
        mRequest.send(token, id)
                .enqueue(object : Callback<EntregoHistoryServiceDetails> {
                    override fun onResponse(call: Call<EntregoHistoryServiceDetails>?, response: Response<EntregoHistoryServiceDetails>?) {
                        when (response?.body()?.code) {
                            0 -> listener?.onSuccessGetHistory(response.body()?.payload!!)
                            else -> listener?.onFailureGetHistory(response?.body()?.code, response?.body()?.message)
                        }
                    }

                    override fun onFailure(call: Call<EntregoHistoryServiceDetails>?, t: Throwable?) {
                        listener?.onFailureGetHistory(null, null)
                    }

                })
    }
}