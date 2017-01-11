package entrego.com.android.storage.model

/**
 * Created by bertalt on 05.12.16.
 */
data class DeliveryModel(val id: Int,
                         val customer: CustomerModel,
                         val status: String,
                         val route: EntregoRouteModel) {


}
