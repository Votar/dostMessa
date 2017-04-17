package com.entregoya.msngr.entity

import java.util.*

class EntregoPriceEntity(val amount: Float = 0f, val currency: String = "$") {

    override fun toString(): String {
        return "EntregoPriceEntity(amount=$amount, currency='$currency')"
    }

    fun toView(): String {
        var currencyView = currency
        if (currency.equals("USD", false))
            currencyView = "$"
        return currencyView + String.format(Locale.getDefault(), "%1\$.02f",amount)
    }
}