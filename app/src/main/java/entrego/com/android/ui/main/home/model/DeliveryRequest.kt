package entrego.com.android.ui.main.home.model

import entrego.com.android.storage.model.DeliveryModel
import entrego.com.android.binding.Delivery
import entrego.com.android.storage.preferences.EntregoStorage
import entrego.com.android.util.Logger
import entrego.com.android.util.event_bus.LogoutEvent
import entrego.com.android.web.api.ApiCreator
import entrego.com.android.web.api.EntregoApi
import entrego.com.android.web.model.request.delivery.EntregoResultGetDelivery
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object DeliveryRequest {

    interface ResultGetDelivery {
        fun onSuccessGetDelivery()
        fun onFailureGetDelivery(code: Int?, message: String?)
    }

    fun requestDelivery(listener: ResultGetDelivery?) {
        val token = EntregoStorage.getToken()
        requestDelivery(token, listener)
    }

    fun requestDelivery(token: String, listener: ResultGetDelivery?) {
        ApiCreator.server.create(EntregoApi.GetDelivery::class.java)
                .getDelivery(token)
                .enqueue(object : Callback<EntregoResultGetDelivery> {
                    override fun onResponse(call: Call<EntregoResultGetDelivery>?, response: Response<EntregoResultGetDelivery>?) {

                        if (response?.body() != null) {

                            val responseResult = response.body()
                            Logger.logd(responseResult.toString())

                            when (responseResult?.code) {
                                0 -> {
                                    if (Delivery.getInstance().history == null)
                                        Delivery.getInstance().update(responseResult.payload)
                                    listener?.onSuccessGetDelivery()
                                }
                                9 -> {
                                    if (Delivery.getInstance().history != null)
                                        Delivery.getInstance().update(null)
                                }
                                2 -> EventBus.getDefault().post(LogoutEvent())
                                else -> listener?.onFailureGetDelivery(responseResult?.code, "")
                            }
                        }
                    }

                    override fun onFailure(call: Call<EntregoResultGetDelivery>?, t: Throwable?) {
                        listener?.onFailureGetDelivery(null, "")
                    }

                })

    }

    fun updateDelivery(token: String, listener: ResultGetDelivery?) {
        ApiCreator.server.create(EntregoApi.GetDelivery::class.java)
                .getDelivery(token)
                .enqueue(object : Callback<EntregoResultGetDelivery> {
                    override fun onResponse(call: Call<EntregoResultGetDelivery>?, response: Response<EntregoResultGetDelivery>?) {

                        response?.body()?.apply {
                            when (code) {
                                0 -> {
                                    Delivery.getInstance().update(payload)
                                    listener?.onSuccessGetDelivery()
                                }
                                9 -> Delivery.getInstance().update(null)
                                2 -> EventBus.getDefault().post(LogoutEvent())

                                else -> listener?.onFailureGetDelivery(code, message)
                            }
                        }
                    }

                    override fun onFailure(call: Call<EntregoResultGetDelivery>?, t: Throwable?) {
                        listener?.onFailureGetDelivery(null, t?.message)
                    }
                })
    }
}