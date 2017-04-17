package com.entregoya.msngr.web.model.response.delivery

import com.entregoya.msngr.storage.model.DeliveryModel
import com.entregoya.msngr.web.model.response.EntregoResult

class EntregoResultHistoryDelivery(code: Int?,
                                   message: String?,
                                   val payload: Array<DeliveryModel>
                            ) : EntregoResult(code, message) {
}