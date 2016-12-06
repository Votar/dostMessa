package entrego.com.entrego.storage.model

import com.google.android.gms.maps.model.LatLng

/**
 * Created by bertalt on 05.12.16.
 */
class RouteModel(val start: LatLng,
                 val destination: LatLng) {

    override fun toString(): String {
        return "RouteModel(destination=$destination, start=$start)"
    }
}