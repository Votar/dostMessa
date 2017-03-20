package entrego.com.android.web.api

interface ApiContract {
    companion object{
        const val RESPONSE_OK = 0
        const val RESPONSE_INVALID_TOKEN = 2
        const val INCORRECT_ORDER_STATE = 8
    }
}