package com.entregoya.msngr.binding

/**
 * Created by bertalt on 22.12.16.
 */
class HistoryServiceBinding(
        val id: Int,
        val time: String,
        val type: String,
        val price: Float
) {

    fun getFormattedPrice():String{
        return "\$$price"
    }
}