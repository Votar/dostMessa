package com.entregoya.msngr.web.model.response.statistic

import com.entregoya.msngr.entity.IncomeEntity
import com.entregoya.msngr.web.model.response.EntregoResult

class EntregoResultIncomes(code: Int?,
                           message: String?,
                           val payload: Array<IncomeEntity>) : EntregoResult(code, message) {
}