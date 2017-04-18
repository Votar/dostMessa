package com.entregoya.msngr.ui.account.help.reports.create.model

import com.entregoya.msngr.web.api.ApiContract
import com.entregoya.msngr.web.api.ApiCreator
import com.entregoya.msngr.web.api.EntregoApi
import com.entregoya.msngr.web.model.request.common.SendReportBody
import com.entregoya.msngr.web.model.response.EntregoResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

class SendReportRequest {
    companion object {
        const val END_POINT = "messenger/delivery/incident"
    }

    interface Request {
        @Headers(EntregoApi.CONTENT_JSON)
        @POST(END_POINT)
        fun parameters(@Header(EntregoApi.TOKEN) token: String, @Body body: SendReportBody): Call<EntregoResult>
    }

    interface SendReportRequestListener {
        fun onSuccessSendReportRequest()
        fun onFailureSendReportRequest(code: Int?, message: String?)
    }

    fun executeAsync(token: String, orderId: Long, message: String, listener: SendReportRequestListener?) {

        ApiCreator.server.create(Request::class.java)
                .parameters(token, SendReportBody(orderId, message))
                .enqueue(object : Callback<EntregoResult> {
                    override fun onResponse(call: Call<EntregoResult>?, response: Response<EntregoResult>?) {
                        if (response?.body() != null)
                            response.body().apply {
                                when (code) {
                                    ApiContract.RESPONSE_OK -> listener?.onSuccessSendReportRequest()
                                    else -> listener?.onFailureSendReportRequest(code, message)
                                }
                            }
                        else
                            listener?.onFailureSendReportRequest(null, null)
                    }

                    override fun onFailure(call: Call<EntregoResult>?, t: Throwable?) {
                        listener?.onFailureSendReportRequest(null, null)
                    }
                })
    }

}