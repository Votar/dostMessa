package entrego.com.android.ui.main.home

import android.app.ProgressDialog
import android.content.*
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import com.google.maps.android.PolyUtil
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import entrego.com.android.R
import entrego.com.android.binding.Delivery
import entrego.com.android.databinding.FragmentHomeBinding
import entrego.com.android.location.LocationTracker
import entrego.com.android.storage.model.EntregoRouteModel
import entrego.com.android.storage.model.OrderStatus
import entrego.com.android.storage.preferences.EntregoStorage
import entrego.com.android.ui.main.accept.AcceptDeliveryFragment
import entrego.com.android.ui.main.delivery.description.DescriptionFragment
import entrego.com.android.ui.main.dialog.GPSRequiredFragment
import entrego.com.android.ui.main.home.presenter.HomePresenter
import entrego.com.android.ui.main.home.presenter.IHomePresenter
import entrego.com.android.ui.main.home.view.IHomeView
import entrego.com.android.util.isGpsEnable
import entrego.com.android.util.loading
import entrego.com.android.util.snackSimple
import entrego.com.android.web.socket.SocketService
import kotlinx.android.synthetic.main.connect_selector.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.include_navigation.*
import java.util.*


class HomeFragment : Fragment(), OnMapReadyCallback, IHomeView {

    companion object {
        val REQUEST_ACCESS_FINE_LOCATION = 0x811
    }

    val mPresenter: IHomePresenter = HomePresenter()

    var mTimer: Timer? = null
    //Dnipro
    val defaulLocation = LatLng(8.386368, -81.0629982)
    var mCurrentLocation = defaulLocation
    var mMap: GoogleMap? = null
    var mPolylinePath: ArrayList<Polyline> = ArrayList()
    var mBinder: FragmentHomeBinding? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binder: FragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binder.delivery = Delivery.getInstance()
        retainInstance = true
        val mapView = binder.mapView
        mapView.onCreate(savedInstanceState)
        mapView.onResume()

        try {
            MapsInitializer.initialize(activity.applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        mBinder = binder
        return binder.root
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        switcher_disconnected.setOnCheckedChangeListener { button, state ->
            run {
                if (state) {
                    LocalBroadcastManager.getInstance(context).sendBroadcast(Intent(SocketService.ACTION_FILTER)
                            .putExtra(SocketService.KEY_EVENT, SocketService.SocketEvent.DISCONNECT.value))
                    val token = EntregoStorage.getToken()
                    mPresenter.sendOffline(token)
                    stopLocationTracker()
                }
            }
        }
        switcher_connected.setOnCheckedChangeListener { button, state ->
            run {
                if (state)
                    startLocationTracker()
            }
        }
        switcher_disconnected.isChecked = true

    }

    override fun onStart() {
        super.onStart()
        mPresenter.onStart(this)

        reenterTransition = true
        map_view.getMapAsync(this)
        LocalBroadcastManager.getInstance(context).registerReceiver(mReceiverCurrentLocation,
                IntentFilter(LocationTracker.BROADCAST_ACTION_CURRENT_LOCATION))
        home_my_loc.setOnClickListener { moveCameraToCurrentLocation() }
        if (switcher_connected.isChecked)
            startLocationTracker()
        else
            stopLocationTracker()
    }

    private fun stopLocationTracker() {
        mTimer?.cancel()
        mTimer?.purge()
        mTimer = null
    }

    private fun startLocationTracker() {

        if (mTimer != null) return

        if (isGpsEnable(activity))
            GPSRequiredFragment.dismiss(activity.supportFragmentManager)
        else
            GPSRequiredFragment.show(activity.supportFragmentManager)

        mTimer = Timer()
        LocalBroadcastManager
                .getInstance(context)
                .sendBroadcast(Intent(SocketService.ACTION_FILTER)
                        .putExtra(SocketService.KEY_EVENT, SocketService.SocketEvent.CONNECT.value))
        mTimer?.schedule(object : TimerTask() {
            override fun run() {
                val token = EntregoStorage.getToken()
                LocationTracker.sendLocation(token, mCurrentLocation)

                LocalBroadcastManager
                        .getInstance(context)
                        .sendBroadcast(Intent(SocketService.ACTION_FILTER)
                                .putExtra(SocketService.KEY_EVENT, SocketService.SocketEvent.SEND_TEXT.value))
            }
        }, 2000, 3000)
    }

    override fun onMapReady(map: GoogleMap?) {
        mMap = map
        val settings = mMap!!.uiSettings
        settings.setCompassEnabled(false)
        settings.setZoomControlsEnabled(false)
        settings.setMapToolbarEnabled(false)
        moveCamera(mCurrentLocation.latitude, mCurrentLocation.longitude)
        mPresenter.onBuildView()
    }


    override fun onResume() {
        super.onResume()
        map_view.onResume()
    }

    override fun onPause() {
        super.onPause()
        map_view.onPause()
    }

    override fun onStop() {
        super.onStop()
        mPresenter.onStop()
        LocalBroadcastManager.getInstance(context).unregisterReceiver(mReceiverCurrentLocation)
        stopLocationTracker()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    var mProgress: ProgressDialog? = null
    override fun showProgress() {
        mProgress = ProgressDialog(activity)
        mProgress?.loading()
    }

    override fun hideProgress() {
        mProgress?.dismiss()
    }

    override fun showMessage(idString: Int) {
        view?.snackSimple(getString(idString))
    }


    override fun buildPath(path: String) {
        removePolyline()
        val points = PolyUtil.decode(path)
        val polylineOptions = PolylineOptions().geodesic(true).color(resources.getColor(R.color.colorDarkBlue)).width(10f)
        for (i in 0..points.size - 1)
            polylineOptions.add(points[i])
        if (mMap != null) {
            mPolylinePath.add(mMap!!.addPolyline(polylineOptions))
        }
    }

    override fun getFragmentContext(): Context {
        return context
    }

    override fun prepareNoDelivery() {
        removePolyline()
        AcceptDeliveryFragment.dismiss(activity.supportFragmentManager)
        mMap?.clear()
        sliding_layout?.panelState = SlidingUpPanelLayout.PanelState.HIDDEN
        val description = fragmentManager.findFragmentByTag(DescriptionFragment.TAG)
        if (description != null)
            fragmentManager.beginTransaction()
                    .remove(description)
                    .commit()
    }

    fun removePolyline() {
        for (nextLine in mPolylinePath)
            nextLine.remove()
        mPolylinePath.clear()
    }

    var startMarker: Marker? = null
    var finishMarker: Marker? = null
    var wayPointsMarker: Array<Marker>? = null
    override fun prepareRoute(route: EntregoRouteModel) {

        mBinder?.delivery = Delivery.getInstance()
        mBinder?.invalidateAll()

        startMarker?.remove()
        finishMarker?.remove()
        sliding_layout?.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED

        //add start point
        val startLatLng = LatLng(route.getCurrentPoint().point.latitude, route.getCurrentPoint().point.longitude)
        startMarker = mMap?.addMarker(MarkerOptions()
                .position(startLatLng)
                .draggable(false)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .title(getString(R.string.start_point)))

        //add finish point
        val finishLatLng = LatLng(route.getDestinationPoint().point.latitude,
                route.getDestinationPoint().point.longitude)
        finishMarker = mMap?.addMarker(MarkerOptions()
                .position(finishLatLng)
                .draggable(false)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                .title(getString(R.string.finish_point)))

        if (mBinder?.delivery?.status == OrderStatus.PENDING.value)
            moveCamera(startLatLng.latitude, startLatLng.longitude)

        if (home_sliding_container != null) {
            val fragment = DescriptionFragment.getInstance()
            if (!fragment.isAdded && activity != null) {
                activity.supportFragmentManager.beginTransaction()
                        .replace(R.id.home_sliding_container, fragment, DescriptionFragment.TAG)
                        .commit()
            }
        }

        navigation_clickable_ll.setOnClickListener {
            showNavigation(route.getCurrentPoint().point, route.getDestinationPoint().point)
        }
        startLocationTracker()
    }


    fun showNavigation(source: LatLng, destination: LatLng) {

        val sourceLat = source.latitude
        val sourceLon = source.longitude
        val destLat = destination.latitude
        val destLon = destination.longitude
        try {
            val uri = String.format("http://maps.google.com/maps?saddr=$sourceLat,$sourceLon&daddr=$destLat,$destLon")
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity")
            startActivity(intent)
        } catch (ex: ActivityNotFoundException) {
            mPresenter.noGoogleMapsException()
        }
    }

    fun moveCameraToCurrentLocation() {
        moveCamera(mCurrentLocation.latitude, mCurrentLocation.longitude)
    }

    var currentLocMarker: Marker? = null
    val mReceiverCurrentLocation = object : BroadcastReceiver() {
        override fun onReceive(ctx: Context?, intent: Intent?) {
            val lat = intent?.getDoubleExtra(LocationTracker.CUR_LAT, 0.0)!!
            val lon = intent?.getDoubleExtra(LocationTracker.CUR_LON, 0.0)!!
            if (lat != 0.0 && lon != 0.0) {
                mCurrentLocation = LatLng(lat, lon)
                currentLocMarker?.remove()
                currentLocMarker = mMap?.addMarker(MarkerOptions()
                        .position(mCurrentLocation)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_user_pin))
                        .draggable(false))
            }
        }
    }

    override fun moveCamera(latitude: Double, longitude: Double) {
        val nextCamera = CameraUpdateFactory
                .newLatLngZoom(LatLng(latitude, longitude), 16f)
        mMap?.moveCamera(nextCamera)
    }

    override fun showAcceptFragment() {
        AcceptDeliveryFragment.show(activity.supportFragmentManager)
    }

    override fun dissmissAcceptFragment() {
        AcceptDeliveryFragment.dismiss(activity.supportFragmentManager)
    }

    override fun showAlertNoGoogleMaps() {
        AlertDialog.Builder(activity)
                .setTitle(R.string.text_error)
                .setMessage(R.string.text_no_google_maps)
                .setPositiveButton(android.R.string.ok, DialogInterface.OnClickListener { dialog, which -> })
                .show()
    }

    val mGpsSwitchStateReceiver = object : BroadcastReceiver() {
        override fun onReceive(ctx: Context, p1: Intent?) {
            if (!isGpsEnable(ctx)) {
                GPSRequiredFragment.show(activity.supportFragmentManager)
                val token = EntregoStorage.getToken()
                switcher_connected.isChecked = false
                switcher_disconnected.isChecked = true
            }
        }
    }
}