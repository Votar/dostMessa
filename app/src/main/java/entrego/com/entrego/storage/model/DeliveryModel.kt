package entrego.com.entrego.storage.model

/**
 * Created by bertalt on 05.12.16.
 */
data class DeliveryModel(val id: Int,
                    val customer: CustomerModel,
                    val route: EntregoRouteModel) {


}
