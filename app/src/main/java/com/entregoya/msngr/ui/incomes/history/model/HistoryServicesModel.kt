package com.entregoya.msngr.ui.incomes.history.model

import com.entregoya.msngr.binding.HistoryServiceBinding
import com.entregoya.msngr.entity.HistoryServicesPreviewEntity
import com.entregoya.msngr.util.formattedDate
import com.entregoya.msngr.web.api.ApiCreator
import com.entregoya.msngr.web.api.EntregoApi
import com.entregoya.msngr.web.model.request.statistic.IncomesBody
import com.entregoya.msngr.web.model.response.statistic.EntregoResultHistoryService
import org.jetbrains.annotations.Nullable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import java.util.*


object HistoryServicesModel {

    const val END_POINT = "messenger/statistics/history/orders"

    interface GetServiceHistoryApi {
        @Headers(EntregoApi.CONTENT_JSON)
        @POST(END_POINT)
        fun send(@Header(EntregoApi.TOKEN) token: String, @Body body: IncomesBody): Call<EntregoResultHistoryService>
    }

    val mRequest = ApiCreator.server.create(GetServiceHistoryApi::class.java)

    interface GetHistoryServices {
        fun onSuccessGetHistory(resultList: List<HistoryServicesPreviewEntity>)
        fun onFailureGetHistory(@Nullable message: String?)
    }

    fun refresh(token: String, range: Pair<Long, Long>, @Nullable listener: GetHistoryServices?) {

        val zone = TimeZone.getDefault()
        val body = IncomesBody(range.first.formattedDate(), range.second.formattedDate(), zone.id)
        mRequest.send(token, body)
                .enqueue(object : Callback<EntregoResultHistoryService> {
                    override fun onFailure(call: Call<EntregoResultHistoryService>?, t: Throwable?) {
                        listener?.onFailureGetHistory(null)
                    }

                    override fun onResponse(call: Call<EntregoResultHistoryService>?, response: Response<EntregoResultHistoryService>?) {
                        when (response?.body()?.code) {
                            0 -> listener?.onSuccessGetHistory(response.body()?.payload!!.toList())
                            else -> listener?.onFailureGetHistory(null)
                        }
                    }
                })

    }

    fun getServiceForToday(): List<HistoryServiceBinding> {


        val list: ArrayList<HistoryServiceBinding> = ArrayList()
        for (i in 0..8)
            list.add(HistoryServiceBinding(0, "8:30pm", "Shipments", 19.40f))
        return list
    }

    fun getServiceRecent(): List<HistoryServiceBinding> {

        val list: ArrayList<HistoryServiceBinding> = ArrayList()
        for (i in 0..8)
            list.add(HistoryServiceBinding(0, "8:30pm", "Shipments", 19.40f))
        return list
    }
}