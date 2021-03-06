package com.entregoya.msngr.location

import android.Manifest
import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.media.RingtoneManager
import android.os.Bundle
import android.os.IBinder
import android.support.v4.app.ActivityCompat
import android.support.v4.app.NotificationCompat
import android.support.v4.content.LocalBroadcastManager
import com.entregoya.msngr.R
import com.entregoya.msngr.binding.Delivery
import com.entregoya.msngr.storage.model.OrderStatus
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.PendingResult
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.entregoya.msngr.storage.preferences.EntregoStorage
import com.entregoya.msngr.ui.main.RootActivity
import com.entregoya.msngr.ui.main.home.model.DeliveryRequest
import com.entregoya.msngr.util.GsonHolder
import com.entregoya.msngr.util.Logger
import com.entregoya.msngr.util.Logger.logd
import com.entregoya.msngr.util.event_bus.LogoutEvent
import com.entregoya.msngr.web.api.ApiCreator
import com.entregoya.msngr.web.api.EntregoApi
import com.entregoya.msngr.web.model.response.EntregoResult
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class TrackService : Service(), GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    companion object {
        const val INTERVAL = 10_000L //10 sec
        const val KEY_LATLNG = "ext_k_lat_lng"
        const val BROADCAST_ACTION_CURRENT_LOCATION = "entrego.com.android.location.current_location"

        const val KEY_ONOFF = "ext_k_lat_lng"
        const val BROADCAST_ACTION_ONLINE_OFFLINE = "entrego.com.android.location.onoff"
    }

    val TAG = "TrackService"
    var mTimer: Timer? = null
    var mUpdateLocStatus: PendingResult<Status>? = null
    var isNeedSendLocation = false
    override fun onBind(intent: Intent?): IBinder {
        throw NotImplementedError()
    }

    var mGoogleApiClient: GoogleApiClient? = null
    var mCurrentLocation: LatLng? = null
    override fun onCreate() {
        logd("track service started")
        super.onCreate()
        mGoogleApiClient = GoogleApiClient.Builder(this)    // setup API
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build()

        mGoogleApiClient?.connect()

        registerOnOffReceiver()

    }

    private fun registerOnOffReceiver() {
        LocalBroadcastManager.getInstance(this).registerReceiver(
                mBroadcastOnOff,
                IntentFilter(BROADCAST_ACTION_ONLINE_OFFLINE))

    }

    private fun unregisterOnOffReceiver() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcastOnOff)
    }

    override fun onDestroy() {
        super.onDestroy()
        mGoogleApiClient?.apply {
            if (isConnected)
                LocationServices.FusedLocationApi.removeLocationUpdates(this, mLocLis)
        }
        unregisterOnOffReceiver()
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
        val workTask = object : TimerTask() {
            override fun run() {
                getUserLocation().apply {
                    if (this == null)
                        logd("Location not available yet")
                    else
                        sendLocation(this)
                }
            }
        }
        mTimer?.schedule(workTask, 0, INTERVAL)
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

    @SuppressLint("MissingPermission")
    fun startLocationListener() {
        val locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY// use GPS
        locationRequest.interval = 5000

        mUpdateLocStatus = LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient,
                locationRequest,
                mLocLis)

        if (isNeedSendLocation)
            startLocationTracker()

    }

    val mLocLis = LocationListener {
        it?.apply {
            mCurrentLocation = LatLng(it.latitude, it.longitude)
            val intent = Intent(BROADCAST_ACTION_CURRENT_LOCATION)
            val jsonLatLng = GsonHolder.instance.toJson(mCurrentLocation, LatLng::class.java)
            intent.putExtra(KEY_LATLNG, jsonLatLng)
            LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent)
        }
    }

    val mBroadcastOnOff = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            if (intent.hasExtra(KEY_ONOFF)) {
                isNeedSendLocation = intent.getBooleanExtra(KEY_ONOFF, false)
                if (isNeedSendLocation)
                    startLocationTracker()
                else
                    stopLocationTracker()
            } else throw IllegalStateException("No key on/off in intent")
        }

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
                                0 -> DeliveryRequest.requestDelivery(token, requestDeliveryListener)
                                2 -> EventBus.getDefault().post(LogoutEvent())
                            }
                    }

                    override fun onFailure(call: Call<EntregoResult>?, t: Throwable?) {
                        Logger.loge(TAG, "LocationTrack failed")
                    }
                })


    }

    val requestDeliveryListener = object : DeliveryRequest.ResultGetDelivery {
        override fun onSuccessGetDelivery() {
            if (Delivery.getInstance().status.equals(OrderStatus.PENDING.value, true))
            sendNewDeliveryReceivedNotification()
        }

        override fun onFailureGetDelivery(code: Int?, message: String?) {

        }
    }

    fun sendNewDeliveryReceivedNotification() {

        val mBuilder: NotificationCompat.Builder =
                NotificationCompat.Builder(applicationContext)
                        .setContentTitle(applicationContext.getString(R.string.notification_received_delivery))
                        .setSmallIcon(R.drawable.map_user_pin)
                        .setContentText(applicationContext.getString(R.string.notification_message_delivery))

        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        mBuilder.setSound(alarmSound)
        mBuilder.setDefaults(Notification.DEFAULT_SOUND or Notification.DEFAULT_LIGHTS or Notification.DEFAULT_VIBRATE)
        val resultIntent = Intent(applicationContext, RootActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }

        val resultPendingIntent =
                PendingIntent.getActivity(
                        applicationContext,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_ONE_SHOT
                )
        mBuilder.setContentIntent(resultPendingIntent)
        val mNotifyMgr = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        mNotifyMgr.notify(42, mBuilder.build())
    }

}