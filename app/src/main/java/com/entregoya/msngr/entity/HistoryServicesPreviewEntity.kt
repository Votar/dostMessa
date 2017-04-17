package com.entregoya.msngr.entity

import com.entregoya.msngr.util.toDateTimeLong
import com.entregoya.msngr.util.toFormattedDateTime
import com.entregoya.msngr.entity.EntregoPriceEntity

data class HistoryServicesPreviewEntity(val id:Int, val price: EntregoPriceEntity, val completed: String){

    override fun toString(): String {
        return "HistoryServicesPreviewEntity(id=$id, price=$price, completed='"+completed.toDateTimeLong()
    }

    fun formattedCompletedDate():String{
        return completed.toFormattedDateTime()
    }
}