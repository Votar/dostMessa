package com.entregoya.msngr.ui.account.help.reports.model

import com.entregoya.msngr.web.api.ApiContract
import com.entregoya.msngr.web.api.ApiCreator
import com.entregoya.msngr.web.api.EntregoApi
import com.entregoya.msngr.web.model.response.common.ReportListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers


class ReportListRequest {
    companion object {
        const val END_POINT = "messenger/delivery/incident"
    }

    interface Request {
        @Headers(EntregoApi.CONTENT_JSON)
        @GET(END_POINT)
        fun parameters(@Header(EntregoApi.TOKEN) token: String): Call<ReportListResponse>
    }

    interface ReportListRequestListener {
        fun onSuccessReportListRequest(resultList: Array<ReportEntity>)
        fun onFailureReportListRequest(code: Int?, message: String?)
    }

    fun executeAsync(token: String, listener: ReportListRequestListener?) {
        ApiCreator.server.create(Request::class.java)
                .parameters(token)
                .enqueue(object : Callback<ReportListResponse> {
                    override fun onFailure(call: Call<ReportListResponse>?, t: Throwable?) {
                        listener?.onFailureReportListRequest(null, null)
                    }

                    override fun onResponse(call: Call<ReportListResponse>?, response: Response<ReportListResponse>?) {
                        if (response?.body() != null)
                            response.body().apply {
                                when (code) {
                                    ApiContract.RESPONSE_OK -> listener?.onSuccessReportListRequest(payload)
                                    else -> listener?.onFailureReportListRequest(code, message)
                                }
                            }
                        else
                            listener?.onFailureReportListRequest(null, null)
                    }

                })
    }

}