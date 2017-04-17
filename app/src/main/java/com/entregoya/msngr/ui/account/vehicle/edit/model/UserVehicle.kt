package com.entregoya.msngr.ui.account.vehicle.edit.model

import android.content.Context
import android.support.annotation.Nullable
import com.google.gson.Gson
import com.entregoya.msngr.storage.model.EntregoPhoneModel
import com.entregoya.msngr.storage.model.UserProfileModel
import com.entregoya.msngr.storage.model.UserVehicleModel
import com.entregoya.msngr.storage.preferences.EntregoStorage
import com.entregoya.msngr.web.api.ApiCreator
import com.entregoya.msngr.web.api.EntregoApi
import com.entregoya.msngr.ui.account.profile.UserProfile
import com.entregoya.msngr.util.event_bus.LogoutEvent
import com.entregoya.msngr.web.model.response.EntregoResult
import com.entregoya.msngr.web.model.response.common.FieldErrorResponse
import com.entregoya.msngr.web.model.response.profile.EntregoResultEditVehicle
import com.entregoya.msngr.web.model.response.profile.EntregoResultGetProfile
import com.entregoya.msngr.web.model.response.profile.EntregoResultGetVehicle
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object UserVehicle {

    interface ResultListener {
        fun onSuccessRefresh(userVehicle: UserVehicleModel)
        fun onFailureRefresh(message: String?)
    }

    interface ResultUpdateListener {
        fun onSuccessUpdate(userVehicle: UserVehicleModel)
        fun onFailureUpdate(message: String)
        fun onFieldValidatorError(field: FieldErrorResponse)

    }

    fun getVehicle(): UserVehicleModel? {
        return EntregoStorage.getUserVehicle()
    }


    fun refresh(context: Context, listener: ResultListener?, cash: Boolean = true) {

        val token = EntregoStorage.getToken()
        ApiCreator.server.create(EntregoApi.GetVehicle::class.java)
                .getVehicle(token)
                .enqueue(object : Callback<EntregoResultGetVehicle> {
                    override fun onResponse(call: Call<EntregoResultGetVehicle>?, response: Response<EntregoResultGetVehicle>?) {
                        if (response?.body() != null) {
                            val responseBody = response.body()
                            when (response.body()?.code) {
                                0 -> {

                                    if (cash)
                                        EntregoStorage.setUserVehicle(responseBody?.payload)

                                    listener?.onSuccessRefresh(responseBody?.payload!!)
                                }
                                2 ->EventBus.getDefault().post(LogoutEvent())
                                else -> listener?.onFailureRefresh(responseBody?.message!!)
                            }
                        } else {
                            if (response?.errorBody() != null) {
                                val errorBody = response.errorBody()!!
                                listener?.onFailureRefresh(null)
                            }
                        }
                    }

                    override fun onFailure(call: Call<EntregoResultGetVehicle>?, t: Throwable?) {
                        listener?.onFailureRefresh("")
                    }

                })

    }

    fun update(token: String, vehicle: UserVehicleModel, @Nullable listener: UserVehicle.ResultUpdateListener?) {
        ApiCreator.server.create(EntregoApi.UpdateVehicle::class.java)
                .updateVehicle(token, vehicle)
                .enqueue(object : Callback<EntregoResultEditVehicle> {
                    override fun onResponse(call: Call<EntregoResultEditVehicle>?, response: Response<EntregoResultEditVehicle>?) {
                        if (response?.body() != null) {
                            val responseBody = response.body()!!
                            when (responseBody.code) {
                                0 -> listener?.onSuccessUpdate(responseBody.payload!!)

                                1 -> for (next in responseBody.fields)
                                    listener?.onFieldValidatorError(next)

                                else -> listener?.onFailureUpdate(responseBody.message)
                            }
                        } else {
                            if (response?.errorBody() != null) {
                                val errorBody = response.errorBody()
                                listener?.onFailureUpdate("")
                            }
                        }
                    }

                    override fun onFailure(call: Call<EntregoResultEditVehicle>?, t: Throwable?) {
                        listener?.onFailureUpdate("")
                    }

                })


    }

}