package entrego.com.entrego.web.model.request.common

import android.content.Context
import com.google.gson.Gson
import entrego.com.entrego.storage.model.UserProfileModel
import entrego.com.entrego.storage.model.UserVehicleModel
import entrego.com.entrego.storage.preferences.EntregoStorage
import entrego.com.entrego.web.api.ApiCreator
import entrego.com.entrego.web.api.EntregoApi
import entrego.com.entrego.web.model.response.EntregoResult
import entrego.com.entrego.web.model.response.profile.EntregoResultGetProfile
import entrego.com.entrego.web.model.response.profile.EntregoResultGetVehicle
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by bertalt on 30.11.16.
 */
object UserVehicle {

    interface ResultListener {
        fun onSuccessRefresh(userVehicle: UserVehicleModel)
        fun onFailureRefresh(message: String)
    }

    fun getVehicle(context: Context): UserProfileModel? {
        return EntregoStorage(context).getUserProfile()
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
                                    EntregoStorage(context).setUserVehicle(responseBody?.payload)

                                    if (cash)
                                        EntregoStorage(context).setUserVehicle(responseBody?.payload)

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

                    override fun onFailure(call: Call<EntregoResultGetVehicle>?, t: Throwable?) {
                        listener?.onFailureRefresh("")
                    }

                })

    }

}