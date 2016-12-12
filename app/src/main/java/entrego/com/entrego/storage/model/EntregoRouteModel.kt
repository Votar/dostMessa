package entrego.com.entrego.storage.model

import com.google.android.gms.maps.model.LatLng
import entrego.com.entrego.storage.model.binding.EntregoPointBinding

/**
 * Created by bertalt on 05.12.16.
 */
class EntregoRouteModel(val start: EntregoPointBinding,
                        val destination: EntregoPointBinding) {


    fun toList() : List<EntregoPointBinding>{

        return listOf(start, destination)
    }

}