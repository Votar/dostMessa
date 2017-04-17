package com.entregoya.msngr.web.model.response.statistic

import com.entregoya.msngr.storage.model.DeliveryModel
import com.entregoya.msngr.web.model.response.EntregoResult

class EntregoHistoryServiceDetails(code: Int?,
                                   message: String?,
                                   val payload: DeliveryModel) : EntregoResult(code, message) {
}