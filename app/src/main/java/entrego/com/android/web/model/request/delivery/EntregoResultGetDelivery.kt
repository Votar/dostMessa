package entrego.com.android.web.model.request.delivery

import entrego.com.android.storage.model.DeliveryModel

/**
 * Created by bertalt on 06.12.16.
 */
class EntregoResultGetDelivery(val code: Int,
                               val message: String?,
                               val payload: DeliveryModel?) {


    override fun toString(): String {
        return "EntregoResultGetDelivery(code=$code, message=$message, payload=$payload)"
    }

}