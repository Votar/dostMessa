package com.entregoya.msngr.ui.main.home.model

import com.entregoya.msngr.web.api.ApiCreator
import com.entregoya.msngr.web.api.EntregoApi
import com.entregoya.msngr.web.model.response.CommonResponseListener
import com.entregoya.msngr.web.model.response.EntregoResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers


object OfflineRequest {

    const val END_POINT = "messenger/user/offline"

    interface API {
        @Headers(EntregoApi.CONTENT_JSON)
        @GET(END_POINT)
        fun send(@Header(EntregoApi.TOKEN) token: String): Call<EntregoResult>
    }

    private val mRequest = ApiCreator.server.create(API::class.java)

    fun request(token: String, listener: CommonResponseListener?) {
        mRequest.send(token)
                .enqueue(object : Callback<EntregoResult> {
                    override fun onResponse(call: Call<EntregoResult>?, response: Response<EntregoResult>?) {
                        when (response?.body()?.code) {
                            0 -> listener?.onSuccessResponse()
                            else -> listener?.onFailureResponse(response?.body()?.code, response?.body()?.message)
                        }
                    }

                    override fun onFailure(call: Call<EntregoResult>?, t: Throwable?) {
                        listener?.onFailureResponse(null, null)
                    }

                })
    }
}