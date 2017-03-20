package entrego.com.android.ui.main.delivery.description.cancel.model

import android.os.Handler
import android.os.Looper
import android.support.annotation.Nullable
import com.google.gson.JsonElement
import entrego.com.android.binding.Delivery
import entrego.com.android.ui.main.accept.model.DeliveryInteractor
import entrego.com.android.util.Logger
import entrego.com.android.web.api.ApiCreator
import entrego.com.android.web.api.EntregoApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object CancelDelivery {

    interface CancelDeliveryListener {
        fun onSuccessCancel()
        fun onFailureCancel(message: String?, code: Int?)
    }

    var isRequested: Boolean = false

    fun executeAsync(token: String, deliveryId: Long, reason: String, @Nullable listener: CancelDelivery.CancelDeliveryListener?) {

        if (isRequested)
            return

        isRequested = true

        ApiCreator.server.create(EntregoApi.CancelDelivery::class.java)
                .cancelDelivery(token, deliveryId)
                .enqueue(object : Callback<JsonElement> {
                    override fun onFailure(call: Call<JsonElement>?, t: Throwable?) {
                        listener?.onFailureCancel(null, null)
                        isRequested = false
                    }

                    override fun onResponse(call: Call<JsonElement>?, response: Response<JsonElement>?) {
                        listener?.onSuccessCancel()
                        Delivery.getInstance().update(null)
                        Logger.logd(response?.body().toString())
                        isRequested = false
                    }
                })
    }
}