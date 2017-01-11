package entrego.com.android.ui.registration.model

import entrego.com.android.storage.model.EntregoPhoneModel
import entrego.com.android.web.api.ApiCreator
import entrego.com.android.web.api.EntregoApi
import entrego.com.android.web.model.request.registration.RegistrationBody
import entrego.com.android.web.model.response.common.FieldErrorResponse
import entrego.com.android.web.model.response.registration.EntregoResultRegistration
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        fun onFailureRegistration(message: String?, code: Int?)
        fun onValidationError(field: FieldErrorResponse)
    }

    fun requestAsync(listener: ResultListener) {

        val body = RegistrationBody(email, name, password, EntregoPhoneModel(phoneCode, phoneNumber))

        ApiCreator.server.create(EntregoApi.Registration::class.java).registration(body)
                .enqueue(object : Callback<EntregoResultRegistration> {
                    override fun onResponse(call: Call<EntregoResultRegistration>?, response: Response<EntregoResultRegistration>?) {
                        if (response?.body() != null) {
                            val result = response?.body()!!
                            when (result.code) {
                                0 -> listener.onSuccessRegistration()
                                1 -> if (result.fields.isNotEmpty()) {
                                    for (next in result.fields)
                                        listener.onValidationError(next)
                                }
                                else -> listener.onFailureRegistration("", result.code)

                            }
                        }

                    }

                    override fun onFailure(call: Call<EntregoResultRegistration>?, t: Throwable?) {
                        listener.onFailureRegistration(t?.message, null)
                    }

                })
    }
}