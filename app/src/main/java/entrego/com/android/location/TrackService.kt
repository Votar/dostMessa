package entrego.com.android.location

import android.Manifest
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.IBinder
import android.support.v4.app.ActivityCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.PendingResult
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import entrego.com.android.storage.preferences.EntregoStorage
import entrego.com.android.ui.main.home.model.DeliveryRequest
import entrego.com.android.util.Logger
import entrego.com.android.util.Logger.logd
import entrego.com.android.util.event_bus.LogoutEvent
import entrego.com.android.web.api.ApiCreator
import entrego.com.android.web.api.EntregoApi
import entrego.com.android.web.model.response.EntregoResult
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class TrackService : Service(), GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {


    var mTimer: Timer? = null
    var mUpdateLocStatus: PendingResult<Status>? = null
    override fun onBind(intent: Intent?): IBinder {
        throw NotImplementedError()
    }

    lateinit var mGoogleApiClient: GoogleApiClient
    var mCurrentLocation: LatLng? = null
    override fun onCreate() {
        logd("track service started")
        super.onCreate()
        mGoogleApiClient = GoogleApiClient.Builder(this)    // setup API
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build()

        mGoogleApiClient.connect()

    }

    override fun onDestroy() {
        super.onDestroy()
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, mLocLis)
        stopLocationTracker()
    }

    private fun stopLocationTracker() {
        mTimer?.cancel()
        mTimer?.purge()
        mTimer = null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }

    private fun startLocationTracker() {

        if (mTimer != null) return

        mTimer = Timer()
        mTimer?.schedule(mWorkTask, 0, 5000)
    }

    override fun onConnected(p0: Bundle?) {
        startLocationListener()
    }

    override fun onConnectionSuspended(p0: Int) {
        logd("Connection suspended")
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        logd("google api client connection failed")
        //TODO : Notify for failure
    }

    fun startLocationListener() {
        val locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY// use GPS
        locationRequest.interval = 5000

        mUpdateLocStatus = LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient
                , locationRequest,
                mLocLis)
        startLocationTracker()

    }

    val mLocLis = LocationListener {
        mCurrentLocation = LatLng(it.latitude, it.longitude)
    }

    fun getUserLocation(): LatLng? {
        if (mUpdateLocStatus == null) {
            return null
        } else
            return mCurrentLocation
    }


    fun sendLocation(location: LatLng) {
        val token = EntregoStorage.getToken()

        ApiCreator.server.create(EntregoApi.PostLocation::class.java)
                .postLocation(token, location)
                .enqueue(object : Callback<EntregoResult> {
                    override fun onResponse(call: Call<EntregoResult>?, response: Response<EntregoResult>?) {
                        if (response?.body() != null)
                            when (response.body().code) {
                                0 -> DeliveryRequest.requestDelivery(token, null)
                                2 -> EventBus.getDefault().post(LogoutEvent())
                            }
                    }

                    override fun onFailure(call: Call<EntregoResult>?, t: Throwable?) {
                        Logger.loge(LocationTracker::class.simpleName, "LocationTrack failed")
                    }
                })


    }

    val mWorkTask = object : TimerTask() {
        override fun run() {
            getUserLocation().apply {
                if (this == null)
                    logd("Location not available yet")
                else
                    sendLocation(this)
            }
        }
    }
}