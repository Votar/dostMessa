package com.entregoya.msngr.ui.auth.model

import com.entregoya.msngr.storage.preferences.EntregoStorage
import com.entregoya.msngr.util.Logger
import com.entregoya.msngr.web.api.ApiCreator
import com.entregoya.msngr.web.api.EntregoApi
import com.entregoya.msngr.web.model.request.auth.AuthBody
import com.entregoya.msngr.web.model.response.EntregoResult
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
                            Logger.logd(response.body()?.toString())
                            when (response.body()?.code) {
                                0 -> {
                                    val token = response.headers()?.get(EntregoApi.TOKEN)
                                    EntregoStorage.setLastEmail(email)
                                    listener.onSuccessAuth(token)
                                }
                                else -> listener.onFailureAuth(response.body()?.message)

                            }
                        }
                    }

                    override fun onFailure(call: Call<EntregoResult>?, t: Throwable?) {
                        listener.onFailureAuth("")
                    }

                })
    }
}