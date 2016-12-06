package entrego.com.entrego.location

import android.content.Context
import android.content.Intent

/**
 * Created by bertalt on 05.12.16.
 */
object LocationTracker {

    const val BROADCAST_ACTION_CURRENT_LOCATION = "com.entrego.location.current_location"
    const val BROADCAST_CURRENT_LOCATION = "key.current.location"

    const val CUR_LAT = "key_lat"
    const val CUR_LON = "key_lon"

    fun startLocationTracker(context: Context) {
        val intent = Intent(context, LocationService::class.java)

        context.stopService(intent)
        context.startService(intent)
    }
}