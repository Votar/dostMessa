package entrego.com.android.entity

class EntregoPriceEntity(val amount: Float = 0f, val currency: String = "$") {

    override fun toString(): String {
        return "EntregoPriceEntity(amount=$amount, currency='$currency')"
    }

    fun toView(): String {
        var currencyView = currency
        if (currency.equals("USD", false))
            currencyView= "$"
            return "$currencyView $amount"
    }
}