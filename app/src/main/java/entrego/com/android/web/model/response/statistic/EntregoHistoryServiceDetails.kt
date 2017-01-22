package entrego.com.android.web.model.response.statistic

import entrego.com.android.storage.model.DeliveryModel
import entrego.com.android.web.model.response.EntregoResult

class EntregoHistoryServiceDetails(code: Int?,
                                   message: String?,
                                   val payload: DeliveryModel) : EntregoResult(code, message) {
}