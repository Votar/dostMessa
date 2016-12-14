package entrego.com.entrego.ui.main.home

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.LocalBroadcastManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import entrego.com.entrego.R
import entrego.com.entrego.location.LocationTracker
import entrego.com.entrego.location.diraction.Route
import entrego.com.entrego.storage.model.CustomerModel
import entrego.com.entrego.storage.model.DeliveryModel
import entrego.com.entrego.storage.model.EntregoRouteModel
import entrego.com.entrego.storage.model.binding.DeliveryInstance
import entrego.com.entrego.ui.main.delivery.description.DescriptionFragment
import entrego.com.entrego.ui.main.home.presenter.HomePresenter
import entrego.com.entrego.ui.main.home.presenter.IHomePresenter
import entrego.com.entrego.ui.main.home.view.IHomeView
import entrego.com.entrego.util.UserMessageUtil
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*

import android.net.Uri
import kotlinx.android.synthetic.main.include_navigation.*


/**
 * Created by bertalt on 05.12.16.
 */
class HomeFragment : Fragment(), OnMapReadyCallback, IHomeView {
    companion object {
        val REQUEST_ACCESS_FINE_LOCATION = 0x811
    }

    val presenter: IHomePresenter = HomePresenter()

    var mCurrentLocation: LatLng? = null
    var mMap: GoogleMap? = null
    var mPolylinePath: ArrayList<Polyline> = ArrayList()


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater?.inflate(R.layout.fragment_home, container, false)

        val mapView = view!!.findViewById(R.id.map_view) as MapView

        mapView.onCreate(savedInstanceState)
        mapView.onResume()

        try {
            MapsInitializer.initialize(activity.applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return view
    }


    override fun onStart() {
        super.onStart()

        reenterTransition = true
        map_view.getMapAsync(this)

        LocalBroadcastManager.getInstance(context).registerReceiver(mReceiverCurrentLocation,
                IntentFilter(LocationTracker.BROADCAST_ACTION_CURRENT_LOCATION))

        home_my_loc.setOnClickListener { moveCameraToCurrentLocation() }
    }

    override fun onMapReady(map: GoogleMap?) {
        mMap = map

        val settings = mMap!!.uiSettings

        settings.setCompassEnabled(false)
        settings.setZoomControlsEnabled(false)
        settings.setMapToolbarEnabled(false);
        //TODO:Remove on real devices
        val dnipro = LatLng(48.4619585, 34.7201766)
        moveCamera(dnipro.latitude, dnipro.longitude)

        presenter.onStart(this)

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
        presenter.onStop()
        LocalBroadcastManager.getInstance(context).unregisterReceiver(mReceiverCurrentLocation)
    }

    override fun onDestroy() {
        super.onDestroy()
    }


    override fun buildPath(route: Route) {

        removePolyline()

        val polylineOptions = PolylineOptions().geodesic(true).color(Color.BLUE).width(10f)
        for (i in 0..route.points.size - 1)
            polylineOptions.add(route.points[i])

        if (mMap != null) {
            mPolylinePath.add(mMap!!.addPolyline(polylineOptions))
        }

        val routeBounds = LatLngBounds(route.bounds.southwest, route.bounds.northeast)
        mMap?.moveCamera(CameraUpdateFactory.newLatLngBounds(routeBounds, 60))
    }

    override fun getFragmentContext(): Context {
        return context
    }


    override fun showMessage(message: String) {
        UserMessageUtil.show(context, message)
    }


    override fun prepareNoDelivery() {

        removePolyline()
        mMap?.clear()
        home_navigation_ll?.visibility = View.GONE

        if (sliding_layout != null) {
            sliding_layout.panelState = SlidingUpPanelLayout.PanelState.HIDDEN
//            home_sliding_container.visibility = View.GONE
        }

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

    override fun prepareRoute(route: EntregoRouteModel) {

        //add start point
        val startLatLng = LatLng(route.start.latitude, route.start.longitude)
        mMap?.addMarker(MarkerOptions()
                .position(startLatLng)
                .draggable(false)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.start_pin))
                .title(getString(R.string.start_point)))

        //add finish point
        val finishLatLng = LatLng(route.destination.latitude, route.destination.longitude)
        mMap?.addMarker(MarkerOptions()
                .position(finishLatLng)
                .draggable(false)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.finish_pin))
                .title(getString(R.string.finish_point)))

        if (home_sliding_container != null) {
            home_sliding_container.visibility = View.VISIBLE
            sliding_layout.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
            val fragment = DescriptionFragment.getInstance()

            fragmentManager.beginTransaction()
                    .replace(R.id.home_sliding_container, fragment, DescriptionFragment.TAG)
                    .commit()
        }

        home_navigation_ll.visibility = View.VISIBLE
        if (route.destination.address != null)
            home_navigation_address.text = route.destination.address

        navigation_clickable_ll.setOnClickListener { showNavigation(startLatLng, finishLatLng) }

    }


    fun showNavigation(source: LatLng, destination: LatLng) {

        val sourceLat = source.latitude
        val sourceLon = source.longitude
        val destLat = destination.latitude
        val destLon = destination.longitude

        val uri = String.format("http://maps.google.com/maps?saddr=$sourceLat,$sourceLon&daddr=$destLat,$destLon")
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity")
        startActivity(intent)
    }


    fun moveCameraToCurrentLocation() {
        if (mCurrentLocation != null) {
            moveCamera(mCurrentLocation!!.latitude, mCurrentLocation!!.longitude)
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

    override fun moveCamera(latitude: Double, longitude: Double) {
        val nextCamera = CameraUpdateFactory
                .newLatLngZoom(LatLng(latitude, longitude), 16f)

        mMap?.animateCamera(nextCamera)
    }
}