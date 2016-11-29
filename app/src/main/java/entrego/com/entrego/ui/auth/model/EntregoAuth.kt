package entrego.com.entrego.ui.auth.model

import com.google.gson.Gson
import com.google.gson.JsonElement
import entrego.com.entrego.util.Logger
import entrego.com.entrego.web.api.ApiCreator
import entrego.com.entrego.web.api.EntregoApi
import entrego.com.entrego.web.model.request.auth.AuthBody
import entrego.com.entrego.web.model.request.registration.EntregoResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.*

/**
 * Created by bertalt on 29.11.16.
 */
class EntregoAuth(val email: String, val password: String) {

    interface ResultListener {
        fun onSuccessAuth()
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
                                0 -> listener.onSuccessAuth()
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