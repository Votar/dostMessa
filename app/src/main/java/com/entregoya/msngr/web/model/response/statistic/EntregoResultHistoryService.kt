package com.entregoya.msngr.web.model.response.statistic

import com.entregoya.msngr.entity.HistoryServicesPreviewEntity
import com.entregoya.msngr.web.model.response.EntregoResult

class EntregoResultHistoryService(code: Int?,
                           message: String?,
                           val payload: Array<HistoryServicesPreviewEntity>) : EntregoResult(code, message) {
}