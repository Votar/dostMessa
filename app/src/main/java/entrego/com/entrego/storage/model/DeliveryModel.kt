package entrego.com.entrego.storage.model

/**
 * Created by bertalt on 05.12.16.
 */
class DeliveryModel(val id: Int,
                    val customer: CustomerModel,
                    val route: EntregoRouteModel) {


    override fun toString(): String {
        return "DeliveryModel(id=$id, customer=$customer, route=$route)"
    }
}
