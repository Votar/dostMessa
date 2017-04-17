package com.entregoya.msngr.ui.account.profile

import android.content.Context
import android.support.annotation.Nullable
import com.google.gson.Gson
import com.entregoya.msngr.binding.UserProfileEntity
import com.entregoya.msngr.storage.model.EntregoPhoneModel
import com.entregoya.msngr.storage.model.UserProfileModel
import com.entregoya.msngr.storage.preferences.EntregoStorage
import com.entregoya.msngr.web.api.ApiCreator
import com.entregoya.msngr.web.api.EntregoApi
import com.entregoya.msngr.web.model.request.common.ChangePasswordBody
import com.entregoya.msngr.web.model.response.EntregoResult
import com.entregoya.msngr.web.model.response.common.FieldErrorResponse
import com.entregoya.msngr.web.model.response.profile.EntregoResultEditPassword
import com.entregoya.msngr.web.model.response.profile.EntregoResultEditProfile
import com.entregoya.msngr.web.model.response.profile.EntregoResultGetProfile
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


object UserProfile {

    interface ResultRefreshListener {
        fun onSuccessRefresh(userProfile: UserProfileModel)
        fun onFailureRefresh(message: String?)
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


    fun getProfile(): UserProfileModel? {
        return EntregoStorage.getUserProfile()
    }


    fun refresh(context: Context, @Nullable listener: ResultRefreshListener?) {

        val token = EntregoStorage.getToken()
        ApiCreator.server.create(EntregoApi.GetProfile::class.java)
                .getProfile(token)
                .enqueue(object : Callback<EntregoResultGetProfile> {
                    override fun onResponse(call: Call<EntregoResultGetProfile>?, response: Response<EntregoResultGetProfile>?) {
                        when (response?.body()?.code) {
                            0 -> {
                                EntregoStorage.setUserProfile(response.body()?.payload)
                                UserProfileEntity.getInstance().update(response.body()?.payload)
                                listener?.onSuccessRefresh(response.body()?.payload!!)
                            }
                            else -> listener?.onFailureRefresh(response?.body()?.message)
                        }

                    }

                    override fun onFailure(call: Call<EntregoResultGetProfile>?, t: Throwable?) {
                        listener?.onFailureRefresh("")
                    }

                })

    }


    fun update(context: Context, email: String, name: String, phoneCode: String, phoneNumber: String,
               @Nullable listener: ResultUpdateListener?) {
        val token = EntregoStorage.getToken()
        val body = UserProfileModel(0, email, name, EntregoPhoneModel(phoneCode, phoneNumber))
        ApiCreator.server.create(EntregoApi.UpdateProfile::class.java)
                .updateProfile(token, body)
                .enqueue(object : Callback<EntregoResultEditProfile> {
                    override fun onResponse(call: Call<EntregoResultEditProfile>?, response: Response<EntregoResultEditProfile>?) {
                        if (response?.body() != null) {
                            val responseBody = response.body()
                            when (responseBody.code) {
                                0 -> {
                                    EntregoStorage.setUserProfile(responseBody.payload)
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
                                val errorBody = response.errorBody()
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

        val token = EntregoStorage.getToken()
        val body = ChangePasswordBody(newPassword)
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