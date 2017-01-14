package entrego.com.android.ui.main.drawer.model

import com.google.gson.Gson
import entrego.com.android.storage.model.DeliveryModel
import entrego.com.android.storage.model.PointStatus
import entrego.com.android.util.Logger
import entrego.com.android.web.api.ApiCreator
import entrego.com.android.web.api.EntregoApi
import entrego.com.android.web.model.request.delivery.ChangeStatusBody
import entrego.com.android.web.model.response.EntregoResult
import entrego.com.android.web.model.response.delivery.EntregoResultStatusChanged
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Admin on 14.01.2017.
 */
object DrawerModel {
    interface ChangeStatusListener {
        fun onSuccessChange(updatedDelivery: DeliveryModel)
        fun onFailureChange(code: Int?, message: String?)
    }

    val statusRequest = ApiCreator.server.create(EntregoApi.ChangeStatus::class.java)

    fun nextStatus(token: String, deliveryId: Int, status: PointStatus, listener: ChangeStatusListener?) {
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