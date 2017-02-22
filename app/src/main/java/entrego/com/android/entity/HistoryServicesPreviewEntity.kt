package entrego.com.android.entity

import entrego.com.android.util.toDateTimeLong
import entrego.com.android.util.toFormattedDateTime
import entrego.com.android.entity.EntregoPriceEntity

data class HistoryServicesPreviewEntity(val id:Int, val price: EntregoPriceEntity, val completed: String){

    override fun toString(): String {
        return "HistoryServicesPreviewEntity(id=$id, price=$price, completed='"+completed.toDateTimeLong()
    }

    fun formattedCompletedDate():String{
        return completed.toFormattedDateTime()
    }
}