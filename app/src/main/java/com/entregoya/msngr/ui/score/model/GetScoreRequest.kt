package com.entregoya.msngr.ui.score.model

import com.entregoya.msngr.web.api.ApiContract
import com.entregoya.msngr.web.api.ApiCreator
import com.entregoya.msngr.web.api.EntregoApi
import com.entregoya.msngr.web.model.response.statistic.EntregoScoreEntity
import com.entregoya.msngr.web.model.response.statistic.EntregoScoreResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

class GetScoreRequest {
    companion object {
        const val END_POINT = "messenger/statistics/score"
    }

    interface Request {
        @Headers(EntregoApi.CONTENT_JSON)
        @GET(END_POINT)
        fun parameters(@Header(EntregoApi.TOKEN) token: String): Call<EntregoScoreResponse>
    }

    interface GetScoreRequestListener {
        fun onSuccessGetScoreRequest(response: EntregoScoreEntity)
        fun onFailureGetScoreRequest(code: Int?, message: String?)
    }

    fun executeAsync(token: String, listener: GetScoreRequestListener?) {
        ApiCreator.server.create(Request::class.java)
                .parameters(token)
                .enqueue(object : Callback<EntregoScoreResponse> {
                    override fun onResponse(call: Call<EntregoScoreResponse>?, response: Response<EntregoScoreResponse>?) {
                        if (response?.body() != null)
                            response.body().apply {
                                when (code) {
                                    ApiContract.RESPONSE_OK -> listener?.onSuccessGetScoreRequest(payload)
                                    else -> listener?.onFailureGetScoreRequest(code, message)
                                }
                            }
                    }

                    override fun onFailure(call: Call<EntregoScoreResponse>?, t: Throwable?) {
                        listener?.onFailureGetScoreRequest(null, null)
                    }
                })

    }

}