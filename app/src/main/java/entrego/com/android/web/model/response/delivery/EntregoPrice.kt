package entrego.com.android.web.model.response.delivery

/**
 * Created by Admin on 18.01.2017.
 */
class EntregoPrice(val amount: Float, val currency: String) {

    override fun toString(): String {
        return "EntregoPrice(amount=$amount, currency='$currency')"
    }

    fun toView(): String {
        return "$amount $currency"
    }
}