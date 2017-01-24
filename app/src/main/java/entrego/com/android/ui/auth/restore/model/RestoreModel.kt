package entrego.com.android.ui.auth.restore.model

import entrego.com.android.web.api.ApiCreator
import entrego.com.android.web.api.EntregoApi
import entrego.com.android.web.model.request.auth.RestorePasswordBody
import entrego.com.android.web.model.response.CommonResponseListener
import entrego.com.android.web.model.response.EntregoResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object RestoreModel {

    val request = ApiCreator.server.create(EntregoApi.RestorePassword::class.java)
    fun restorePassword(email: String, listener: CommonResponseListener?) {
        request.restorePassword(RestorePasswordBody(email))
                .enqueue(object : Callback<EntregoResult> {
                    override fun onResponse(call: Call<EntregoResult>?, response: Response<EntregoResult>?) {
                        when (response?.body()?.code) {
                            0 -> listener?.onSuccessResponse()
                            else -> listener?.onFailureResponse(response?.body()?.code, response?.body()?.message)
                        }
                    }
                    override fun onFailure(call: Call<EntregoResult>?, t: Throwable?) {
                        listener?.onFailureResponse(null, null)
                    }
                })
    }
}