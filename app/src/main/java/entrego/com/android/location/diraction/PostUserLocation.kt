package entrego.com.android.location.diraction

import android.support.annotation.Nullable
import com.google.android.gms.maps.model.LatLng
import com.google.gson.JsonElement
import entrego.com.android.util.Logger
import entrego.com.android.web.api.ApiCreator
import entrego.com.android.web.api.EntregoApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by bertalt on 20.12.16.
 */
object PostUserLocation {

    interface PostLocationListener {
        fun onSuccessPostLocation()
        fun onFailurePostLocation(message: String?)
    }

    fun sendAsync(token: String, currentLocation: LatLng, @Nullable listener: PostUserLocation?) {

//        ApiCreator.server.create(EntregoApi.PostLocation::class.java)
//                .postLocation(token)
//                .enqueue(object : Callback<JsonElement> {
//                    override fun onFailure(call: Call<JsonElement>?, t: Throwable?) {
//                        Logger.loge("network", "post location failed")
//                    }
//
//                    override fun onResponse(call: Call<JsonElement>?, response: Response<JsonElement>?) {
//                        Logger.logd(response?.body().toString())
//                    }
//
//                })
    }
}