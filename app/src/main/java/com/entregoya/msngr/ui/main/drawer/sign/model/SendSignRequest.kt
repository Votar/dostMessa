package com.entregoya.msngr.ui.main.drawer.sign.model

import android.graphics.Bitmap
import android.os.Handler
import android.os.Looper
import android.support.annotation.Nullable
import com.entregoya.msngr.util.encodeToStringBase64
import com.entregoya.msngr.web.api.ApiCreator
import com.entregoya.msngr.web.api.EntregoApi
import com.entregoya.msngr.web.model.request.delivery.FinishDeliveryBody
import com.entregoya.msngr.web.model.response.EntregoResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by bertalt on 13.12.16.
 */
object SendSignRequest {

    interface SendSignListener {
        fun onSuccessSendSign()
        fun onFailureSendSign(code: Int?, message: String?)
    }

    fun executeAsync(token: String, deliveryId: Long, picture: ByteArray, @Nullable listener: SendSignListener?) {
        val encodedPhoto = picture.encodeToStringBase64()
        val body = FinishDeliveryBody(encodedPhoto)
        ApiCreator.server.create(EntregoApi.FinishDelivery::class.java)
                .finishDelivery(token, deliveryId, body)
                .enqueue(object : Callback<EntregoResult> {
                    override fun onFailure(call: Call<EntregoResult>?, t: Throwable?) {
                        listener?.onFailureSendSign(null, null)
                    }
                    override fun onResponse(call: Call<EntregoResult>?, response: Response<EntregoResult>?) {
                        when (response?.body()?.code) {
                            0 -> listener?.onSuccessSendSign()
                            else -> listener?.onFailureSendSign(null, null)
                        }
                    }
                })
    }


}