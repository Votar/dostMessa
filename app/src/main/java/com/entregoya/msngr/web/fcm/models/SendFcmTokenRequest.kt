package com.entregoya.msngr.web.fcm.models

import com.entregoya.msngr.web.api.ApiCreator
import com.entregoya.msngr.web.api.EntregoApi
import com.entregoya.msngr.web.model.response.EntregoResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

class SendFcmTokenRequest {
    companion object {
        const val END_POINT = "messenger/user/push"
    }

    interface Request {
        @Headers(EntregoApi.CONTENT_JSON)
        @POST(END_POINT)
        fun parameters(@Header(EntregoApi.TOKEN) token: String, @Body body: SendFcmBody): Call<EntregoResult>
    }

    interface ResponseListener {
        fun onSuccess()
        fun onFailure(code: Int?, message: String?)
    }

    fun executeAsync(token: String, pushToken: String, listener: ResponseListener?) {
        val body = SendFcmBody(pushToken)
        ApiCreator.server.create(Request::class.java)
                .parameters(token, body)
                .enqueue(object : Callback<EntregoResult> {
                    override fun onFailure(call: Call<EntregoResult>?, t: Throwable?) {
                        listener?.onFailure(null, null)
                    }

                    override fun onResponse(call: Call<EntregoResult>?, response: Response<EntregoResult>) {
                        if (response.isSuccessful) {

                            listener?.onSuccess()

                        } else listener?.onFailure(response.code(), response.message())
                    }
                })

    }


}