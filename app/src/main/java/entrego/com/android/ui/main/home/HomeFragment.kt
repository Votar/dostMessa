package entrego.com.android.ui.main.home

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.ProgressDialog
import android.content.*
import android.content.Context.NOTIFICATION_SERVICE
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.NotificationCompat
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
import entrego.com.android.binding.HistoryHolder
import entrego.com.android.databinding.FragmentHomeBinding
import entrego.com.android.location.TrackService
import entrego.com.android.storage.model.OrderStatus
import entrego.com.android.storage.preferences.EntregoStorage
import entrego.com.android.ui.main.RootActivity
import entrego.com.android.ui.main.accept.AcceptDeliveryFragment
import entrego.com.android.ui.main.delivery.description.DescriptionFragment
import entrego.com.android.ui.main.home.model.NotificationContract
import entrego.com.android.ui.main.home.presenter.HomePresenter
import entrego.com.android.ui.main.home.presenter.IHomePresenter
import entrego.com.android.ui.main.home.view.IHomeView
import entrego.com.android.util.GsonHolder
import entrego.com.android.util.loading
import entrego.com.android.util.snackSimple
import kotlinx.android.synthetic.main.connect_selector.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.include_navigation.*
import java.util.*


class HomeFragment : Fragment(), OnMapReadyCallback, IHomeView {
    companion object {
    }

    val mPresenter: IHomePresenter = HomePresenter()

    var mTimer: Timer? = null
    //Dnipro
    var mCurrentLocation: LatLng? = null
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
                IntentFilter(TrackService.BROADCAST_ACTION_CURRENT_LOCATION))
        home_my_loc.setOnClickListener { moveCameraToCurrentLocation() }
        if (switcher_connected.isChecked)
            startLocationTracker()
        else
            stopLocationTracker()
    }

    private fun stopLocationTracker() {
        val intent = Intent(TrackService.BROADCAST_ACTION_ONLINE_OFFLINE)
        intent.putExtra(TrackService.KEY_ONOFF, false)
        LocalBroadcastManager.getInstance(activity).sendBroadcast(intent)
    }

    private fun startLocationTracker() {
        val intent = Intent(TrackService.BROADCAST_ACTION_ONLINE_OFFLINE)
        intent.putExtra(TrackService.KEY_ONOFF, true)
        LocalBroadcastManager.getInstance(activity).sendBroadcast(intent)
    }

    override fun onMapReady(map: GoogleMap) {
        mMap = map
        val settings = mMap!!.uiSettings
        settings.setCompassEnabled(false)
        settings.setZoomControlsEnabled(false)
        settings.setMapToolbarEnabled(false)
        if (mCurrentLocation != null)
            moveCameraToCurrentLocation()
        else {
            moveCameraDefaultLocation(map)
        }
        mPresenter.onBuildView()
    }

    private fun moveCameraDefaultLocation(map: GoogleMap) {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(9.0831986,-79.5924046), 11f))
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
        return activity
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
    var wayPointsMarker: LinkedList<Marker> = LinkedList()
    override fun prepareRoute(history: HistoryHolder) {

        mBinder?.delivery = Delivery.getInstance()
        mBinder?.invalidateAll()

        startMarker?.remove()
        finishMarker?.remove()

        sliding_layout?.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED

        //add start point
        val startLatLng = history.getCurrentPoint().waypoint.point
        startMarker = mMap?.addMarker(MarkerOptions()
                .position(startLatLng)
                .draggable(false)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .title(getString(R.string.start_point)))

        //add finish point
        val finishLatLng = history.getDestinationPoint().waypoint.point
        finishMarker = mMap?.addMarker(MarkerOptions()
                .position(finishLatLng)
                .draggable(false)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                .title(getString(R.string.finish_point)))

        wayPointsMarker.forEach { it.remove() }
        wayPointsMarker.clear()
        history.getOtherPoints().forEachIndexed { index, entregoWaypoint ->
            mMap?.let {
                val nextMarker = it.addMarker(
                        MarkerOptions()
                                .position(entregoWaypoint.waypoint.point)
                                .draggable(false)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                )
                wayPointsMarker.add(nextMarker)

            }
        }



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
            showNavigation(startLatLng, finishLatLng)
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
//            intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity")
            startActivity(intent)
        } catch (ex: ActivityNotFoundException) {
            mPresenter.noGoogleMapsException()
        }
    }

    fun moveCameraToCurrentLocation() {
        mCurrentLocation?.apply { moveCamera(latitude, longitude) }
    }

    var currentLocMarker: Marker? = null
    val mReceiverCurrentLocation = object : BroadcastReceiver() {
        override fun onReceive(ctx: Context?, intent: Intent) {

            if (intent.hasExtra(TrackService.KEY_LATLNG)) {
                val jsonLatLng = intent.getStringExtra(TrackService.KEY_LATLNG)
                mCurrentLocation = GsonHolder
                        .instance
                        .fromJson(jsonLatLng, LatLng::class.java)

                mCurrentLocation?.apply {
                    currentLocMarker?.remove()
                    currentLocMarker = mMap?.addMarker(MarkerOptions()
                            .position(this)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_user_pin))
                            .draggable(false))
                }
            } else throw IllegalStateException("No latlng in intent")
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

    override fun dismissAcceptFragment() {
        AcceptDeliveryFragment.dismiss(activity.supportFragmentManager)
    }

    override fun showAlertNoGoogleMaps() {
        AlertDialog.Builder(activity)
                .setTitle(R.string.text_error)
                .setMessage(R.string.text_no_google_maps)
                .setPositiveButton(android.R.string.ok, DialogInterface.OnClickListener { dialog, which -> })
                .show()
    }

    override fun sendDeliveryReceivedNotification() {
        val mBuilder: NotificationCompat.Builder =
                NotificationCompat.Builder(activity)
                        .setContentTitle(getString(R.string.notification_received_delivery))
                        .setSmallIcon(R.drawable.accept_icon)
                        .setContentText(getString(R.string.notification_message_delivery))

        val resultIntent = Intent(activity, RootActivity::class.java)

        val resultPendingIntent =
                PendingIntent.getActivity(
                        activity,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                )

        mBuilder.setContentIntent(resultPendingIntent)

        val mNotifyMgr = activity.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        mNotifyMgr.notify(NotificationContract.NEW_DELIVERY_ID, mBuilder.build())

    }

    override fun errorInSendOffline() {
        switcher_disconnected.isChecked = true
    }

    override fun showMessage(message: String?) {
        view?.snackSimple(message)
    }


}