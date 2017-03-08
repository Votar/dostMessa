package entrego.com.android.entity

import entrego.com.android.binding.EntregoPointBinding
import entrego.com.android.storage.model.PointStatus

data class EntregoWaypoint(val waypoint: EntregoPointBinding,
                           val  status : PointStatus) {


}