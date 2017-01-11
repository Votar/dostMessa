package entrego.com.android.ui.main.accept.model

import com.google.gson.JsonElement
import entrego.com.android.binding.Delivery
import entrego.com.android.util.Logger
import entrego.com.android.web.api.ApiCreator
import entrego.com.android.web.api.EntregoApi
import entrego.com.android.web.model.response.EntregoResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by bertalt on 10.01.17.
 */
object DeliveryInteractor {
    interface ResultListener {
        fun onSuccess()
        fun onFailure(message: String?, code: Int?)
    }

    fun accept(token: String, id: Int, listener: ResultListener?) {
        ApiCreator.server.create(EntregoApi.AcceptDelivery::class.java)
                .acceptDelivery(token, id)
                .enqueue(object : Callback<EntregoResult> {
                    override fun onFailure(call: Call<EntregoResult>?, t: Throwable?) {
                        listener?.onFailure(null, null)
                    }

                    override fun onResponse(call: Call<EntregoResult>?, response: Response<EntregoResult>?) {

                        val result = response?.body()
                        when (result?.code) {
                            0 -> listener?.onSuccess()
                            else -> listener?.onFailure(result?.message, result?.code)
                        }
                        Logger.logd(response?.body().toString())
                    }

                })
    }

    fun decline(token: String, id: Int, listener: ResultListener?) {
        ApiCreator.server.create(EntregoApi.DeclineDelivery::class.java)
                .declineDelivery(token, id)
                .enqueue(object : Callback<EntregoResult> {
                    override fun onFailure(call: Call<EntregoResult>?, t: Throwable?) {
                        listener?.onFailure(null, null)
                    }

                    override fun onResponse(call: Call<EntregoResult>?, response: Response<EntregoResult>?) {
                        val result = response?.body()
                        when (result?.code) {
                            0 -> listener?.onSuccess()
                            else -> listener?.onFailure(result?.message, result?.code)
                        }
                        Logger.logd(response?.body().toString())
                    }

                })
    }


}