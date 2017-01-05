package entrego.com.android.location

import android.app.IntentService
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.google.android.gms.maps.model.LatLng
import entrego.com.android.util.Logger
import java.util.*

/**
 * Created by bertalt on 05.01.17.
 */
class TrackerService(name: String) : IntentService(name) {

    public constructor() : this(TrackerService::class.java.simpleName) {
    }

    val timer: Timer = Timer()
    val dnipro = LatLng(48.4619585, 34.7201766)
    var mCurrentLocation = dnipro

    override fun onCreate() {
        super.onCreate()
        registerReceiver(mReceiverCurrentLocation,
                IntentFilter(LocationTracker.BROADCAST_ACTION_CURRENT_LOCATION))
    }

    override fun onHandleIntent(p0: Intent?) {
        timer.schedule(timerTask, 0, 3000)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mReceiverCurrentLocation)
        Logger.logd("Typo destroyed")

    }

    val timerTask = object : TimerTask() {
        override fun run() {
            Logger.logd("Typo send " + mCurrentLocation.latitude + " " + mCurrentLocation.latitude)
        }
    }

    val mReceiverCurrentLocation = object : BroadcastReceiver() {
        override fun onReceive(ctx: Context?, intent: Intent?) {
            val lat = intent?.getDoubleExtra(LocationTracker.CUR_LAT, 0.0)!!
            val lon = intent?.getDoubleExtra(LocationTracker.CUR_LON, 0.0)!!
            if (lat != 0.0 && lon != 0.0)
                mCurrentLocation = LatLng(lat, lon)
        }
    }
}