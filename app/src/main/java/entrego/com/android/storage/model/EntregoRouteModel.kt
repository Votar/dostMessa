package entrego.com.android.storage.model

import entrego.com.android.binding.EntregoPointBinding

/**
 * Created by bertalt on 05.12.16.
 */
class EntregoRouteModel(val path: EntregoPath,
                        val waypoints: Array<EntregoPointBinding>) {

    //TODO:remove mocked values
    fun getCurrentPoint(): EntregoPointBinding {
        return waypoints[0]
    }

    fun getDestinationPoint(): EntregoPointBinding {
        return waypoints[1]
    }

    fun getNextPoint(): EntregoPointBinding? {
        if (waypoints.size > 2)
            return waypoints[2]
        else
            return null
    }


}