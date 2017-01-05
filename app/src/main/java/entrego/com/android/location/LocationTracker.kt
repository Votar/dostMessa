package entrego.com.android.location

import android.content.Context
import android.content.Intent
import com.google.android.gms.maps.model.LatLng
import com.google.gson.JsonElement
import entrego.com.android.binding.DeliveryInstance
import entrego.com.android.ui.main.home.model.DeliveryRequest
import entrego.com.android.util.Logger
import entrego.com.android.web.api.ApiCreator
import entrego.com.android.web.api.EntregoApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by bertalt on 05.12.16.
 */
object LocationTracker {

    const val BROADCAST_ACTION_CURRENT_LOCATION = "com.entrego.location.current_location"

    const val CUR_LAT = "key_lat"
    const val CUR_LON = "key_lon"

    fun startLocationListener(context: Context) {
        val intent = Intent(context, LocationService::class.java)

        context.stopService(intent)
        context.startService(intent)
    }

    fun sendLocation(token: String, location: LatLng) {

        ApiCreator.server.create(EntregoApi.PostLocation::class.java)
                .postLocation(token, location)
                .enqueue(object : Callback<JsonElement> {
                    override fun onResponse(call: Call<JsonElement>?, response: Response<JsonElement>?) {
                        Logger.logd(response?.body().toString())
                        DeliveryRequest.requestDelivery(token, null)
                    }

                    override fun onFailure(call: Call<JsonElement>?, t: Throwable?) {
                        Logger.loge(LocationTracker::class.simpleName, "LocationTrack failed")
                    }
                })


    }

}