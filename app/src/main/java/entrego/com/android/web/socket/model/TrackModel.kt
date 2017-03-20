package entrego.com.android.web.socket.model

import entrego.com.android.web.socket.model.SocketMessageType
import com.google.android.gms.maps.model.LatLng

data class TrackModel(val id: Long,
                      val type: SocketMessageType,
                      val coordinates: LatLng)