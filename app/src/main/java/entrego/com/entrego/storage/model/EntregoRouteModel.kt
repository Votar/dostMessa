package entrego.com.entrego.storage.model

import com.google.android.gms.maps.model.LatLng

/**
 * Created by bertalt on 05.12.16.
 */
class EntregoRouteModel(val start: LatLng,
                        val destination: LatLng) {

    override fun toString(): String {
        return "EntregoRouteModel(destination=$destination, start=$start)"
    }
}