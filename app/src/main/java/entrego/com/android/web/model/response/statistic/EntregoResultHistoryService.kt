package entrego.com.android.web.model.response.statistic

import entrego.com.android.entity.HistoryServicesPreviewEntity
import entrego.com.android.web.model.response.EntregoResult

class EntregoResultHistoryService(code: Int?,
                           message: String?,
                           val payload: Array<HistoryServicesPreviewEntity>) : EntregoResult(code, message) {
}