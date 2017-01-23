package entrego.com.android.ui.account.vehicle.edit.model

import android.content.Context
import android.support.annotation.Nullable
import com.google.gson.Gson
import entrego.com.android.storage.model.EntregoPhoneModel
import entrego.com.android.storage.model.UserProfileModel
import entrego.com.android.storage.model.UserVehicleModel
import entrego.com.android.storage.preferences.EntregoStorage
import entrego.com.android.web.api.ApiCreator
import entrego.com.android.web.api.EntregoApi
import entrego.com.android.ui.account.profile.UserProfile
import entrego.com.android.web.model.response.EntregoResult
import entrego.com.android.web.model.response.common.FieldErrorResponse
import entrego.com.android.web.model.response.profile.EntregoResultEditVehicle
import entrego.com.android.web.model.response.profile.EntregoResultGetProfile
import entrego.com.android.web.model.response.profile.EntregoResultGetVehicle
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by bertalt on 30.11.16.
 */
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

    fun getVehicle(context: Context): UserVehicleModel? {
        return EntregoStorage(context).getUserVehicle()
    }


    fun refresh(context: Context, listener: ResultListener?, cash: Boolean = true) {

        val token = EntregoStorage(context).getToken()
        ApiCreator.server.create(EntregoApi.GetVehicle::class.java)
                .getVehicle(token)
                .enqueue(object : Callback<EntregoResultGetVehicle> {
                    override fun onResponse(call: Call<EntregoResultGetVehicle>?, response: Response<EntregoResultGetVehicle>?) {
                        if (response?.body() != null) {
                            val responseBody = response?.body()
                            when (response?.body()?.code) {
                                0 -> {

                                    if (cash)
                                        EntregoStorage(context).setUserVehicle(responseBody?.payload)

                                    listener?.onSuccessRefresh(responseBody?.payload!!)
                                }
                                else -> listener?.onFailureRefresh(responseBody?.message!!)
                            }
                        } else {
                            if (response?.errorBody() != null) {
                                val errorBody = response?.errorBody()!!
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
                            val responseBody = response?.body()!!
                            when (responseBody.code) {
                                0 -> listener?.onSuccessUpdate(responseBody.payload!!)

                                1 -> for (next in responseBody.fields)
                                            listener?.onFieldValidatorError(next)

                                else -> listener?.onFailureUpdate(responseBody.message)
                            }
                        } else {
                            if (response?.errorBody() != null) {
                                val errorBody = response?.errorBody()
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