package entrego.com.android.storage.model

import entrego.com.android.entity.EntregoParcelType
import entrego.com.android.entity.EntregoPriceEntity
import entrego.com.android.entity.EntregoServiceCategory
import entrego.com.android.entity.EntregoWaypoint
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.*

data class DeliveryModel(val id: Int,
                         val customer: CustomerModel,
                         val status: String,
                         val history: Array<EntregoWaypoint>,
                         val parcel: EntregoParcelType,
                         val category: EntregoServiceCategory,
                         val path: EntregoPath,
                         val pickup: Long,
                         val price: EntregoPriceEntity ) {

    fun formattedPickup(): String {
        if (pickup <= 0) return ""
        val format = SimpleDateFormat("dd/MM/yy hh:mm aaa", Locale.getDefault())
        val timestamp = format.format(pickup)
        return timestamp
    }

    fun formattedId():String{
        return id.toString()
    }

    fun formattedDistance():String{
        val distanceInMeters = path.distance
        val distanceInKm = BigDecimal(distanceInMeters/1000).setScale(2, RoundingMode.UP).toPlainString()
        return distanceInKm
    }
}
