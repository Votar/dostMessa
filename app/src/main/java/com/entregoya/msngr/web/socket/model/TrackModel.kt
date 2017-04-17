package com.entregoya.msngr.web.socket.model

import com.entregoya.msngr.web.socket.model.SocketMessageType
import com.google.android.gms.maps.model.LatLng

data class TrackModel(val id: Long,
                      val type: SocketMessageType,
                      val coordinates: LatLng)