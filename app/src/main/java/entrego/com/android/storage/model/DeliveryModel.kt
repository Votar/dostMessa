package entrego.com.android.storage.model

import entrego.com.android.web.model.response.delivery.EntregoPrice
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by bertalt on 05.12.16.
 */
data class DeliveryModel(val id: Int,
                         val customer: CustomerModel,
                         val status: String,
                         val route: EntregoRouteModel,
                         val pickup: Long,
                         val price: EntregoPrice) {

    fun formattedPickup(): String {
        if (pickup <= 0) return ""
        val format = SimpleDateFormat("dd-MM-yy hh:mm aaa", Locale.ENGLISH)
        val timestamp = format.format(pickup)
        return timestamp
    }
}
