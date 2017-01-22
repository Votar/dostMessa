package entrego.com.android.entity

import entrego.com.android.util.toDateTimeLong
import entrego.com.android.util.toFormattedDateTime
import entrego.com.android.web.model.response.delivery.EntregoPrice

data class HistoryServicesPreviewEntity(val id:Int, val price: EntregoPrice, val completed: String){

    override fun toString(): String {
        return "HistoryServicesPreviewEntity(id=$id, price=$price, completed='"+completed.toDateTimeLong()
    }

    fun formattedCompletedDate():String{
        return completed.toFormattedDateTime()
    }
}