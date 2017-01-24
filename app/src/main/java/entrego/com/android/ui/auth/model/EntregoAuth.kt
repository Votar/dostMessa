package entrego.com.android.ui.auth.model

import entrego.com.android.storage.preferences.EntregoStorage
import entrego.com.android.util.Logger
import entrego.com.android.web.api.ApiCreator
import entrego.com.android.web.api.EntregoApi
import entrego.com.android.web.model.request.auth.AuthBody
import entrego.com.android.web.model.response.EntregoResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EntregoAuth(val email: String, val password: String) {

    interface ResultListener {
        fun onSuccessAuth(token: String?)
        fun onFailureAuth(message: String?)
    }

    fun requestAsync(listener: ResultListener) {

        val body = AuthBody(email, password)

        ApiCreator.server.create(EntregoApi.Authorization::class.java).auth(body)
                .enqueue(object : Callback<EntregoResult> {
                    override fun onResponse(call: Call<EntregoResult>?, response: Response<EntregoResult>?) {
                        if (response?.body() != null) {
                            Logger.logd(response?.body()?.toString())
                            when (response?.body()?.code) {
                                0 -> {
                                    val token = response?.headers()?.get(EntregoApi.TOKEN)
                                    EntregoStorage.setLastEmail(email)
                                    listener.onSuccessAuth(token)
                                }
                                else -> listener.onFailureAuth(response?.body()?.message)

                            }
                        }
                    }

                    override fun onFailure(call: Call<EntregoResult>?, t: Throwable?) {
                        listener.onFailureAuth("")
                    }

                })
    }
}