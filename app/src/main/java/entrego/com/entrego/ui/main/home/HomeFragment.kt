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
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import entrego.com.entrego.R
import entrego.com.entrego.location.LocationTracker
import entrego.com.entrego.location.diraction.Route
import entrego.com.entrego.ui.main.home.presenter.HomePresenter
import entrego.com.entrego.ui.main.home.presenter.IHomePresenter
import entrego.com.entrego.ui.main.home.view.IHomeView
import entrego.com.entrego.storage.model.DeliveryModel
import entrego.com.entrego.util.Logger
import entrego.com.entrego.util.UserMessageUtil
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*


/**
 * Created by bertalt on 05.12.16.
 */
class HomeFragment : Fragment(), OnMapReadyCallback, IHomeView {
    override fun buildRoute(route: Route) {

        val polylinePaths = ArrayList<Polyline>()

        val polylineOptions = PolylineOptions().geodesic(true).color(Color.BLUE).width(10f)
        for (i in 0..route.points.size - 1)
            polylineOptions.add(route.points[i])

        if (mMap != null) {
            polylinePaths.add(mMap!!.addPolyline(polylineOptions))
        }

        val routeBounds = LatLngBounds(route.bounds.southwest, route.bounds.northeast)
        mMap?.moveCamera(CameraUpdateFactory.newLatLngBounds(routeBounds, 10))
    }

    override fun getFragmentContext(): Context {
        return context
    }


    override fun showMessage(message: String) {
        UserMessageUtil.show(context, message)
    }


    override fun prepareNoDelivery() {

    }

    override fun prepareDelivery(delivery: DeliveryModel) {
        Logger.logd(delivery.toString())
    }


    companion object {
        val REQUEST_ACCESS_FINE_LOCATION = 0x811
    }


    val presenter: IHomePresenter = HomePresenter(this)

    var mMap: GoogleMap? = null
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

        map_view.getMapAsync(this)

        LocalBroadcastManager.getInstance(context).registerReceiver(mReceiverCurrentLocation,
                IntentFilter(LocationTracker.BROADCAST_ACTION_CURRENT_LOCATION))
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

        presenter.onCreate()

    }

    val mReceiverCurrentLocation = object : BroadcastReceiver() {
        override fun onReceive(ctx: Context?, intent: Intent?) {
            val lat = intent?.getDoubleExtra(LocationTracker.CUR_LAT, 0.0)!!
            val lon = intent?.getDoubleExtra(LocationTracker.CUR_LON, 0.0)!!

            moveCamera(lat, lon)
        }

    }

    override fun moveCamera(latitude: Double, longitude: Double) {
        val nextCamera = CameraUpdateFactory
                .newLatLngZoom(LatLng(latitude, longitude), 16f)

        mMap?.animateCamera(nextCamera)
    }
}