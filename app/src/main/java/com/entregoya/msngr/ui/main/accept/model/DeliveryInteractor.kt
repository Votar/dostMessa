package com.entregoya.msngr.ui.main.accept.model

import com.entregoya.msngr.binding.Delivery
import com.entregoya.msngr.ui.main.home.model.DeliveryRequest
import com.entregoya.msngr.util.Logger
import com.entregoya.msngr.web.api.ApiCreator
import com.entregoya.msngr.web.api.EntregoApi
import com.entregoya.msngr.web.contract.ResponseContract
import com.entregoya.msngr.web.model.response.EntregoResult
import com.entregoya.msngr.web.model.response.delivery.EntregoResultStatusChanged
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


object DeliveryInteractor {
    interface ResultListener {
        fun onSuccess()
        fun onFailure(message: String?, code: Int?)
    }

    fun accept(token: String, id: Long, listener: ResultListener?) {
        ApiCreator.server.create(EntregoApi.AcceptDelivery::class.java)
                .acceptDelivery(token, id)
                .enqueue(object : Callback<EntregoResultStatusChanged> {
                    override fun onFailure(call: Call<EntregoResultStatusChanged>?, t: Throwable?) {
                        listener?.onFailure(null, null)
                    }

                    override fun onResponse(call: Call<EntregoResultStatusChanged>?, response: Response<EntregoResultStatusChanged>?) {
                        val result = response?.body()
                        when (result?.code) {
                            0 -> {
                                listener?.onSuccess()
                                Delivery.getInstance().update(result.payload)
                            }
                            8 -> {
                                DeliveryRequest.requestDelivery(null)
                                listener?.onFailure(result.message, result.code)
                            }

                            else -> listener?.onFailure(result?.message, result?.code)
                        }
                        Logger.logd(response?.body().toString())
                    }

                })
    }

    fun decline(token: String, id: Long, listener: ResultListener?) {
        ApiCreator.server.create(EntregoApi.DeclineDelivery::class.java)
                .declineDelivery(token, id)
                .enqueue(object : Callback<EntregoResult> {
                    override fun onFailure(call: Call<EntregoResult>?, t: Throwable?) {
                        listener?.onFailure(null, null)
                    }

                    override fun onResponse(call: Call<EntregoResult>?, response: Response<EntregoResult>?) {
                        val result = response?.body()
                        when (result?.code) {
                            ResponseContract.OK -> listener?.onSuccess()
                            ResponseContract.INCORRECT_ORDER_STATE -> {
                                DeliveryRequest.updateDelivery(token, null)
                                listener?.onFailure(result.message, result.code)
                            }
                            else -> listener?.onFailure(result?.message, result?.code)
                        }
                        Logger.logd(response?.body().toString())
                    }

                })
    }


}