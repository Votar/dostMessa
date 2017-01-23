package entrego.com.android.web.model

import android.content.Context
import entrego.com.android.storage.preferences.EntregoStorage

/**
 * Created by Admin on 23.01.2017.
 */
object AccessToken {

    fun init(context: Context) {
        token = EntregoStorage(context).getToken()
    }

    private var token: String = ""
    fun getToken(): String = token
    fun updateToken(newToken: String) {
        token = newToken
    }

}