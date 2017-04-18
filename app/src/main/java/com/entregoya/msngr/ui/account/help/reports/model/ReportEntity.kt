package com.entregoya.msngr.ui.account.help.reports.model

import com.entregoya.msngr.util.formattedDefaultDate
import java.util.*

data class ReportEntity(val report: String,
                        val completed: Long?,
                        val created: Long,
                        val order: Long,
                        val status: String,
                        val response: String?) {

    fun formattedCreateDate(): String = created.formattedDefaultDate()
    fun orderToView(): String = order.toString()
}