package entrego.com.android.web.model.response.statistic

import entrego.com.android.entity.IncomeEntity
import entrego.com.android.web.model.response.EntregoResult

class EntregoResultIncomes(code: Int?,
                           message: String?,
                           val payload: Array<IncomeEntity>) : EntregoResult(code, message) {
}