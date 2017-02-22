package entrego.com.android.ui.account.vehicle.page.model

import entrego.com.android.storage.model.UserVehicleModel
import entrego.com.android.web.api.ApiCreator
import entrego.com.android.web.api.EntregoApi
import entrego.com.android.web.model.response.profile.EntregoResultGetVehicle
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

class GetVehicleRequest {
    companion object {
        private const val END_POINT = "messenger/user/vehicle"
    }

    interface Request {
        @Headers(EntregoApi.CONTENT_JSON)
        @GET(END_POINT)
        fun parameters(@Header(EntregoApi.TOKEN) token: String): Call<EntregoResultGetVehicle>
    }

    interface ResponseListener {
        fun onSuccessResponse(model: UserVehicleModel)
        fun onFailureResponse(code: Int?, message: String?)
    }

    fun requestAsync(token: String, listener: ResponseListener?): Call<EntregoResultGetVehicle> {
        val request = ApiCreator.server
                .create(Request::class.java)
                .parameters(token)

        request.enqueue(object : Callback<EntregoResultGetVehicle> {
            override fun onFailure(call: Call<EntregoResultGetVehicle>?, t: Throwable?) {
                call?.let {
                    if (!it.isCanceled) {
                        listener?.onFailureResponse(null, t?.message)
                    }
                }
            }

            override fun onResponse(call: Call<EntregoResultGetVehicle>?, response: Response<EntregoResultGetVehicle>?) {
                when (response?.body()?.code) {
                    0 -> {
                        response?.body()?.let {
                            listener?.onSuccessResponse(it.payload)
                        }
                    }
                    else -> listener?.onFailureResponse(response?.body()?.code, response?.body()?.message)
                }
            }
        })

        return request
    }
}