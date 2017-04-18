package com.entregoya.msngr.web.model.response.common

import com.entregoya.msngr.ui.account.help.reports.model.ReportEntity
import com.entregoya.msngr.web.model.response.EntregoResult

class ReportListResponse(code: Int?,
                         message: String?,
                         val payload: Array<ReportEntity>) : EntregoResult(code, message) {
}