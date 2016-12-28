package entrego.com.android.ui.main.home

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.LocalBroadcastManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.google.maps.android.PolyUtil
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import entrego.com.android.R
import entrego.com.android.binding.DeliveryInstance
import entrego.com.android.databinding.FragmentHomeBinding
import entrego.com.android.location.LocationTracker
import entrego.com.android.location.diraction.Route
import entrego.com.android.storage.model.EntregoRouteModel
import entrego.com.android.ui.main.delivery.description.DescriptionFragment
import entrego.com.android.ui.main.home.presenter.HomePresenter
import entrego.com.android.ui.main.home.presenter.IHomePresenter
import entrego.com.android.ui.main.home.view.IHomeView
import entrego.com.android.util.Logger
import entrego.com.android.util.UserMessageUtil
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.include_navigation.*
import java.util.*


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
    var mBinder: FragmentHomeBinding? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        val binder: FragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binder.delivery = DeliveryInstance.getInstance()
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


    override fun buildPath(path: String) {

        removePolyline()

        val points = PolyUtil.decode(path)

        val polylineOptions = PolylineOptions().geodesic(true).color(Color.BLUE).width(10f)

        for (i in 0..points.size - 1)
            polylineOptions.add(points[i])

        if (mMap != null) {
            mPolylinePath.add(mMap!!.addPolyline(polylineOptions))
        }
    }

    override fun getFragmentContext(): Context {
        return context
    }


    override fun showMessage(message: String) {
        UserMessageUtil.show(context, message)
    }


    override fun prepareNoDelivery() {
        Logger.logd("No delivery!"+Thread.currentThread().name)
        removePolyline()
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


        mBinder?.delivery = DeliveryInstance.getInstance()
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

        moveCamera(startLatLng.latitude, startLatLng.longitude)

        if (home_sliding_container != null) {
            val fragment = DescriptionFragment.getInstance()

            fragmentManager.beginTransaction()
                    .replace(R.id.home_sliding_container, fragment, DescriptionFragment.TAG)
                    .commit()
        }

        navigation_clickable_ll.setOnClickListener {
            showNavigation(route.getCurrentPoint().point,
                    route.getDestinationPoint().point)
        }

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