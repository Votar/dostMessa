package entrego.com.android.ui.account.profile

import android.content.Context
import android.support.annotation.Nullable
import com.google.gson.Gson
import entrego.com.android.storage.model.EntregoPhoneModel
import entrego.com.android.storage.model.UserProfileModel
import entrego.com.android.storage.preferences.EntregoStorage
import entrego.com.android.web.api.ApiCreator
import entrego.com.android.web.api.EntregoApi
import entrego.com.android.web.model.request.common.ChangePasswordRequest
import entrego.com.android.web.model.response.EntregoResult
import entrego.com.android.web.model.response.common.FieldErrorResponse
import entrego.com.android.web.model.response.profile.EntregoResultEditPassword
import entrego.com.android.web.model.response.profile.EntregoResultEditProfile
import entrego.com.android.web.model.response.profile.EntregoResultGetProfile
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by bertalt on 30.11.16.
 */
object UserProfile {

    interface ResultRefreshListener {
        fun onSuccessRefresh(userProfile: UserProfileModel)
        fun onFailureRefresh(message: String)
    }

    interface ResultUpdateListener {
        fun onSuccessUpdate(userProfile: UserProfileModel)
        fun onFailureUpdate(message: String)
        fun onFieldError(field: FieldErrorResponse)
    }

    interface ResultUpdatePasswordListener {
        fun onSuccessUpdate()
        fun onFailureUpdate(message: String)
        fun onFieldError(field: FieldErrorResponse)
    }


    fun getProfile(context: Context): UserProfileModel? {
        return EntregoStorage(context).getUserProfile()
    }


    fun refresh(context: Context, @Nullable listener: ResultRefreshListener?) {

        val token = EntregoStorage(context).getToken()
        ApiCreator.server.create(EntregoApi.GetProfile::class.java)
                .getProfile(token)
                .enqueue(object : Callback<EntregoResultGetProfile> {
                    override fun onResponse(call: Call<EntregoResultGetProfile>?, response: Response<EntregoResultGetProfile>?) {
                        if (response?.body() != null) {
                            val responseBody = response?.body()
                            when (responseBody?.code) {
                                0 -> {
                                    EntregoStorage(context).setUserProfile(responseBody?.payload)
                                    listener?.onSuccessRefresh(responseBody?.payload!!)
                                }
                                else -> listener?.onFailureRefresh(responseBody?.message!!)
                            }
                        } else {
                            if (response?.errorBody() != null) {
                                val errorBody = response?.errorBody()!!
                                val resultBody = Gson().fromJson(errorBody.string(), EntregoResult::class.java)
                                listener?.onFailureRefresh(resultBody.message!!)
                            }
                        }
                    }

                    override fun onFailure(call: Call<EntregoResultGetProfile>?, t: Throwable?) {
                        listener?.onFailureRefresh("")
                    }

                })

    }


    fun update(context: Context, email: String, name: String, phoneCode: String, phoneNumber: String,
               @Nullable listener: ResultUpdateListener?) {
        val token = EntregoStorage(context).getToken()
        val body = UserProfileModel(email, name, EntregoPhoneModel(phoneCode, phoneNumber))
        ApiCreator.server.create(EntregoApi.UpdateProfile::class.java)
                .updateProfile(token, body)
                .enqueue(object : Callback<EntregoResultEditProfile> {
                    override fun onResponse(call: Call<EntregoResultEditProfile>?, response: Response<EntregoResultEditProfile>?) {
                        if (response?.body() != null) {
                            val responseBody = response?.body()!!
                            when (responseBody.code) {
                                0 -> {
                                    EntregoStorage(context).setUserProfile(responseBody.payload)
                                    listener?.onSuccessUpdate(responseBody.payload!!)
                                }
                                1 -> {
                                    for (next in responseBody.fields)
                                        listener?.onFieldError(next)
                                }
                                else -> listener?.onFailureUpdate(responseBody.message!!)
                            }
                        } else {
                            if (response?.errorBody() != null) {
                                val errorBody = response?.errorBody()!!
                                listener?.onFailureUpdate("")
                            }
                        }
                    }

                    override fun onFailure(call: Call<EntregoResultEditProfile>?, t: Throwable?) {
                        listener?.onFailureUpdate("")
                    }

                })


    }

    fun updatePassword(context: Context, newPassword: String, @Nullable listener: ResultUpdatePasswordListener) {

        val token = EntregoStorage(context).getToken()
        val body = ChangePasswordRequest(newPassword)
        ApiCreator.server.create(EntregoApi.UpdateProfilePassword::class.java)
                .updateProfile(token, body)
                .enqueue(object : Callback<EntregoResultEditPassword> {
                    override fun onResponse(call: Call<EntregoResultEditPassword>?, response: Response<EntregoResultEditPassword>?) {

                        if (response != null) {
                            if (response.isSuccessful) {
                                val responseBody = response.body()
                                when (responseBody.code) {
                                    0 -> listener.onSuccessUpdate()
                                    1 -> listener.onFieldError(responseBody.fields[0])
                                    else -> listener.onFailureUpdate("")
                                }

                            } else
                                listener.onFailureUpdate("")
                        }
                    }

                    override fun onFailure(call: Call<EntregoResultEditPassword>?, t: Throwable?) {
                        listener.onFailureUpdate("")
                    }

                })

    }

}