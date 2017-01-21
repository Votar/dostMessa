package entrego.com.android.web.model.response.delivery

import entrego.com.android.storage.model.DeliveryModel
import entrego.com.android.web.model.response.EntregoResult

class EntregoResultHistoryDelivery(code: Int?,
                                   message: String?,
                                   val payload: Array<DeliveryModel>
                            ) : EntregoResult(code, message) {
}