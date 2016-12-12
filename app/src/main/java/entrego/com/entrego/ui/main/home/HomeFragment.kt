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
import entrego.com.entrego.ui.main.description.DescriptionFragment
import entrego.com.entrego.ui.main.home.presenter.HomePresenter
import entrego.com.entrego.ui.main.home.presenter.IHomePresenter
import entrego.com.entrego.ui.main.home.view.IHomeView
import entrego.com.entrego.util.UserMessageUtil
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*


/**
 * Created by bertalt on 05.12.16.
 */
class HomeFragment : Fragment(), OnMapReadyCallback, IHomeView {


    val presenter: IHomePresenter = HomePresenter()

    var mCurrentLocation: LatLng? = null
    var mMap: GoogleMap? = null


    override fun buildRoute(route: Route) {

        val polylinePaths = ArrayList<Polyline>()
        val polylineOptions = PolylineOptions().geodesic(true).color(Color.BLUE).width(10f)
        for (i in 0..route.points.size - 1)
            polylineOptions.add(route.points[i])

        if (mMap != null) {
            polylinePaths.add(mMap!!.addPolyline(polylineOptions))
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

        if (sliding_layout != null) {

            sliding_layout.panelState = SlidingUpPanelLayout.PanelState.HIDDEN

        }

        val description = fragmentManager.findFragmentByTag(DescriptionFragment.TAG)
        if (description != null)
            fragmentManager.beginTransaction()
                    .remove(description)
                    .commit()
    }

    override fun prepareRoute(route: EntregoRouteModel, customer: CustomerModel) {

        mMap?.clear()

        //add start point
        val startLatLng = LatLng(route.start.latitude, route.start.longitude)
        mMap?.addMarker(MarkerOptions()
                .position(startLatLng)
                .draggable(false)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.start_pin))
                .title(getString(R.string.start_point))
                .snippet("" + customer.company + "\n" + customer.name))

        //add finish point
        val finishLatLng = LatLng(route.destination.latitude, route.destination.longitude)
        mMap?.addMarker(MarkerOptions()
                .position(finishLatLng)
                .draggable(false)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.finish_pin))
                .title(getString(R.string.finish_point)))


        if (home_sliding_container != null) {

            sliding_layout.panelState = SlidingUpPanelLayout.PanelState.EXPANDED

            val fragment = DescriptionFragment.getInstance()

            fragmentManager.beginTransaction()
                    .replace(R.id.home_sliding_container, fragment, DescriptionFragment.TAG)
                    .commit()

        }


    }


    companion object {
        val REQUEST_ACCESS_FINE_LOCATION = 0x811
    }


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
        presenter.onStart(this)


        reenterTransition = true
        map_view.getMapAsync(this)

        LocalBroadcastManager.getInstance(context).registerReceiver(mReceiverCurrentLocation,
                IntentFilter(LocationTracker.BROADCAST_ACTION_CURRENT_LOCATION))

        home_my_loc.setOnClickListener { moveCameraToCurrentLocation() }
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

    override fun onMapReady(map: GoogleMap?) {
        mMap = map

        val settings = mMap!!.uiSettings

        settings.setCompassEnabled(false)
        settings.setZoomControlsEnabled(false)

        //TODO:Remove on real devices
        val dnipro = LatLng(48.4619585, 34.7201766)
        moveCamera(dnipro.latitude, dnipro.longitude)


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