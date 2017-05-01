package com.entregoya.msngr.ui.main.drawer.model

import com.entregoya.msngr.web.api.ApiContract
import com.entregoya.msngr.web.api.ApiCreator
import com.entregoya.msngr.web.api.EntregoApi
import com.entregoya.msngr.web.model.request.delivery.SendShoppingBody
import com.entregoya.msngr.web.model.response.EntregoResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST


class SendShoppingReceiptRequest {
    companion object {
        const val END_POINT = "messenger/delivery/receipt"
    }

    interface Request {
        @Headers(EntregoApi.CONTENT_JSON)
        @POST(END_POINT)
        fun parameters(@Header(EntregoApi.TOKEN) token: String,
                       @Body body: SendShoppingBody): Call<EntregoResult>
    }

    interface SendShoppingReceiptRequestListener {
        fun onSuccessSendShoppingReceiptRequest()
        fun onFailureSendShoppingReceiptRequest(code: Int?, message: String?)
    }

    fun executeAsync(token: String,
                     orderId: Long,
                     amount: Float,
                     photoBase64: String,
                     listener: SendShoppingReceiptRequestListener?) {
        ApiCreator.server.create(Request::class.java)
                .parameters(token, SendShoppingBody(orderId, amount, photoBase64))
                .enqueue(object : Callback<EntregoResult> {
                    override fun onFailure(call: Call<EntregoResult>?, t: Throwable?) {
                        listener?.onFailureSendShoppingReceiptRequest(null, null)
                    }

                    override fun onResponse(call: Call<EntregoResult>?, response: Response<EntregoResult>?) {
                        if (response?.body() != null)
                            response.body().apply {
                                when (code) {
                                    ApiContract.RESPONSE_OK -> listener?.onSuccessSendShoppingReceiptRequest()
                                    else -> listener?.onFailureSendShoppingReceiptRequest(code, message)
                                }
                            }
                        else listener?.onFailureSendShoppingReceiptRequest(null, null)
                    }

                })
    }

}