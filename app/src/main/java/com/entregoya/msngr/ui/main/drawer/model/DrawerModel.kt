package com.entregoya.msngr.ui.main.drawer.model

import com.google.gson.Gson
import com.entregoya.msngr.storage.model.DeliveryModel
import com.entregoya.msngr.storage.model.PointStatus
import com.entregoya.msngr.util.Logger
import com.entregoya.msngr.web.api.ApiCreator
import com.entregoya.msngr.web.api.EntregoApi
import com.entregoya.msngr.web.model.request.delivery.ChangeStatusBody
import com.entregoya.msngr.web.model.response.EntregoResult
import com.entregoya.msngr.web.model.response.delivery.EntregoResultStatusChanged
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object DrawerModel {
    interface ChangeStatusListener {
        fun onSuccessChange(updatedDelivery: DeliveryModel)
        fun onFailureChange(code: Int?, message: String?)
    }

    val statusRequest = ApiCreator.server.create(EntregoApi.ChangeStatus::class.java)

    fun nextStatus(token: String, deliveryId: Long, status: PointStatus, listener: ChangeStatusListener?) {
        statusRequest.changeStatus(token, deliveryId, ChangeStatusBody(status))
                .enqueue(object : Callback<EntregoResultStatusChanged> {
                    override fun onResponse(call: Call<EntregoResultStatusChanged>?, response: Response<EntregoResultStatusChanged>?) {
                        Logger.logd(response?.body()?.toString())

                        if (response != null) {
                            val body = response.body()
                            when (body.code) {
                                0 -> listener?.onSuccessChange(body.payload)
                                else -> listener?.onFailureChange(body.code, body.message)
                            }
                        } else listener?.onFailureChange(null, null)
                    }

                    override fun onFailure(call: Call<EntregoResultStatusChanged>?, t: Throwable?) {
                        listener?.onFailureChange(null, null)
                    }
                })

    }

}