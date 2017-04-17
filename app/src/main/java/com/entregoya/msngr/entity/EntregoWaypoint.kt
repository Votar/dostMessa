package com.entregoya.msngr.entity

import com.entregoya.msngr.binding.EntregoPointBinding
import com.entregoya.msngr.storage.model.PointStatus

data class EntregoWaypoint(val waypoint: EntregoPointBinding,
                           val  status : PointStatus) {


}