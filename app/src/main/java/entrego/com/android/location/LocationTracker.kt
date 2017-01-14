package entrego.com.android.location

import android.content.Context
import android.content.Intent
import com.google.android.gms.maps.model.LatLng
import entrego.com.android.ui.main.home.model.DeliveryRequest
import entrego.com.android.util.Logger
import entrego.com.android.util.event_bus.LogoutEvent
import entrego.com.android.web.api.ApiCreator
import entrego.com.android.web.api.EntregoApi
import entrego.com.android.web.model.response.EntregoResult
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
                .enqueue(object : Callback<EntregoResult> {
                    override fun onResponse(call: Call<EntregoResult>?, response: Response<EntregoResult>?) {
                        Logger.logd(response?.body().toString())
                        when(response?.body()?.code){
                            0->DeliveryRequest.requestDelivery(token, null)
                            2->EventBus.getDefault().post(LogoutEvent())
                        }
                    }

                    override fun onFailure(call: Call<EntregoResult>?, t: Throwable?) {
                        Logger.loge(LocationTracker::class.simpleName, "LocationTrack failed")
                    }
                })




    }

}