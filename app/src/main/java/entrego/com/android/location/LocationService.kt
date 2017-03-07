package entrego.com.android.location

import android.Manifest
import android.app.IntentService
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.LocalBroadcastManager
import android.util.Log
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.PendingResult
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import entrego.com.android.location.diraction.PostUserLocation
import entrego.com.android.storage.preferences.EntregoStorage
import entrego.com.android.util.Logger

class LocationService(val name: String) : IntentService(name), GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public constructor() : this(LocationService::class.java.simpleName) {
    }

    private var mGoogleApiClient: GoogleApiClient? = null
    var mUpdateLocStatus: PendingResult<Status>? = null


    override fun onHandleIntent(intent: Intent?) {

        mGoogleApiClient = GoogleApiClient.Builder(this)    // setup API
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build()
        mGoogleApiClient?.connect()
    }

    override fun onConnected(p0: Bundle?) {

        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY// use GPS
        mLocationRequest.interval = 1000

        if (mUpdateLocStatus == null) {
            // interface for update location
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                return
            }
            mUpdateLocStatus = LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, locLis)
            Logger.logd("Location track first start")
        } else {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, locLis)
            mUpdateLocStatus = LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, locLis)
            Logger.logd("second start")
        }
    }


    val locLis = LocationListener {
        Logger.logd("Location changed")
        Logger.logd("" + it.latitude + " " + it.longitude)

        val intent = Intent(LocationTracker.BROADCAST_ACTION_CURRENT_LOCATION)

        intent.putExtra(LocationTracker.CUR_LAT, it.latitude)
        intent.putExtra(LocationTracker.CUR_LON, it.longitude)

        LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent)

        val token = EntregoStorage.getToken()
        val curLatLng = LatLng(it.latitude, it.longitude)

    }

    override fun onConnectionSuspended(p0: Int) {

    }


    override fun onConnectionFailed(p0: ConnectionResult) {

    }


}