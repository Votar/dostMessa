package entrego.com.android.ui.account.history.model

import entrego.com.android.storage.model.DeliveryModel
import entrego.com.android.web.api.ApiCreator
import entrego.com.android.web.api.EntregoApi
import entrego.com.android.web.model.response.delivery.EntregoResultHistoryDelivery
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

object DeliveryHistoryModel {

    const val GET_DELIVERY_HISTORY = "messenger/statistics/orders"

    interface GetDeliveryHistoryApi {
        @Headers(EntregoApi.CONTENT_JSON)
        @GET(GET_DELIVERY_HISTORY)
        fun send(@Header(EntregoApi.TOKEN) token: String): Call<EntregoResultHistoryDelivery>
    }

    interface GetDeliveryHistoryListener {
        fun onSuccessGetDeliveryHistory(resultArray: Array<DeliveryModel>)
        fun onFailureGetDeliveryHistory(message: String?)
    }

    val mRequest = ApiCreator.server.create(GetDeliveryHistoryApi::class.java)

    fun requestDeliveryHistory(token: String, listener: GetDeliveryHistoryListener?) {
        mRequest.send(token)
                .enqueue(object : Callback<EntregoResultHistoryDelivery> {
                    override fun onResponse(call: Call<EntregoResultHistoryDelivery>?, response: Response<EntregoResultHistoryDelivery>?) {
                        if (response != null)
                            when (response.body().code) {
                                0 -> listener?.onSuccessGetDeliveryHistory(response.body().payload)
                                else -> listener?.onFailureGetDeliveryHistory(response.body().message)
                            }
                    }

                    override fun onFailure(call: Call<EntregoResultHistoryDelivery>?, t: Throwable?) {
                        listener?.onFailureGetDeliveryHistory(null)
                    }
                })
    }
}