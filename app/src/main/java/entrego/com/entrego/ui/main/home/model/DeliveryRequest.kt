package entrego.com.entrego.ui.main.home.model

import entrego.com.entrego.storage.model.DeliveryModel
import entrego.com.entrego.storage.model.binding.DeliveryInstance
import entrego.com.entrego.ui.main.mvp.model.DeliveryUpdatedEvent
import entrego.com.entrego.util.Logger
import entrego.com.entrego.web.api.ApiCreator
import entrego.com.entrego.web.api.EntregoApi
import entrego.com.entrego.web.model.request.delivery.EntregoResultGetDelivery
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by bertalt on 06.12.16.
 */
object DeliveryRequest {

    interface ResultGetDelivery {
        fun onSuccessGetDelivery()
        fun onFailureGetDelivery(code: Int?, message: String?)
    }

    fun requestDelivery(token: String, listener: ResultGetDelivery?) {
        ApiCreator.server.create(EntregoApi.GetDelivery::class.java)
                .getDelivery(token)
                .enqueue(object : Callback<EntregoResultGetDelivery> {
                    override fun onResponse(call: Call<EntregoResultGetDelivery>?, response: Response<EntregoResultGetDelivery>?) {

                        if (response?.body() != null) {

                            val responseResult = response?.body()!!
                            Logger.logd(responseResult.toString())

                            when (responseResult.code) {
                                0 -> {
                                    DeliveryInstance.getInstance().update(responseResult.payload)
                                    listener?.onSuccessGetDelivery()
                                }

                                else -> listener?.onFailureGetDelivery(responseResult.code, "")
                            }
                        }
                    }

                    override fun onFailure(call: Call<EntregoResultGetDelivery>?, t: Throwable?) {
                        listener?.onFailureGetDelivery(null, "")
                    }

                })

    }
}