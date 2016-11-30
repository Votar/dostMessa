package entrego.com.entrego.ui.auth.model

import com.google.gson.Gson
import com.google.gson.JsonElement
import entrego.com.entrego.util.Logger
import entrego.com.entrego.web.api.ApiCreator
import entrego.com.entrego.web.api.EntregoApi
import entrego.com.entrego.web.model.request.auth.AuthBody
import entrego.com.entrego.storage.model.EntregoPhoneModel
import entrego.com.entrego.web.model.response.EntregoResult
import entrego.com.entrego.web.model.request.registration.RegistrationBody
import entrego.com.entrego.web.model.response.registration.EntregoResultRegistration
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.*

/**
 * Created by bertalt on 29.11.16.
 */
class EntregoRegistration(val email: String,
                          val name: String,
                          val password: String,
                          val phoneCode: String,
                          val phoneNumber: String) {

    interface ResultListener {
        fun onSuccessRegistration()
        fun onFailureRegistration(message: String?)
    }

    fun requestAsync(listener: ResultListener) {

        val body = RegistrationBody(email, name, password, EntregoPhoneModel(phoneCode, phoneNumber))

        ApiCreator.server.create(EntregoApi.Registration::class.java).registration(body)
                .enqueue(object : Callback<EntregoResultRegistration> {
                    override fun onResponse(call: Call<EntregoResultRegistration>?, response: Response<EntregoResultRegistration>?) {
                        if (response?.body() != null) {
                            Logger.logd(response?.body()?.toString())
                            when (response?.body()?.code) {
                                0 -> listener.onSuccessRegistration()
                                else -> listener.onFailureRegistration(response?.body()?.message)

                            }
                        }
                    }

                    override fun onFailure(call: Call<EntregoResultRegistration>?, t: Throwable?) {
                        listener.onFailureRegistration("")
                    }

                })
    }
}