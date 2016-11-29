package entrego.com.entrego.storage.preferences

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by bertalt on 28.11.16.
 */
class EtrneGOStorage private constructor(context: Context) {


    private val storage: SharedPreferences
    private val KEY_TOKEN = "pref_key_token"
    private val PREF_NAME = "etrego_pref_store"

    init {
        storage = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }


    fun getToken(): String {
        return storage.getString(KEY_TOKEN, "")
    }

    fun setToken(token: String) {

        storage.edit().putString(KEY_TOKEN, token).apply()

    }
}