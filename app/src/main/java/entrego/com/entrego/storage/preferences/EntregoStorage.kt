package entrego.com.entrego.storage.preferences

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import entrego.com.entrego.storage.model.UserProfileModel
import entrego.com.entrego.storage.model.UserVehicleModel

/**
 * Created by bertalt on 28.11.16.
 */
class EntregoStorage(context: Context) {


    private val storage: SharedPreferences
    private val KEY_TOKEN = "pref_key_token"
    private val KEY_USER_PROFILE = "pref_key_user_profile"
    private val KEY_USER_VEHICLE = "pref_key_user_vehicle"
    private val PREF_STORAGE_NAME = "etrego_pref_store"

    init {
        storage = context.getSharedPreferences(PREF_STORAGE_NAME, Context.MODE_PRIVATE)
    }


    fun getToken(): String {
        return storage.getString(KEY_TOKEN, "")
    }

    fun setToken(token: String?) {
        if (token != null)
            storage.edit().putString(KEY_TOKEN, token).commit()

    }

    fun setUserProfile(profile: UserProfileModel?) {

        val jsonProfile = Gson().toJson(profile, UserProfileModel::class.java)
        storage.edit().putString(KEY_USER_PROFILE, jsonProfile).commit()
    }

    fun getUserProfile(): UserProfileModel? {

        val jsonProfile = storage.getString(KEY_USER_PROFILE, "")

        if (jsonProfile.isEmpty())
            return null
        else
            return Gson().fromJson(jsonProfile, UserProfileModel::class.java)

    }

    fun getUserVehicle(): UserVehicleModel? {

        val jsonProfile = storage.getString(KEY_USER_VEHICLE, "")

        if (jsonProfile.isEmpty())
            return null
        else
            return Gson().fromJson(jsonProfile, UserVehicleModel::class.java)

    }

    fun setUserVehicle(profile: UserVehicleModel?) {

        val jsonVehicle = Gson().toJson(profile, UserVehicleModel::class.java)
        storage.edit().putString(KEY_USER_VEHICLE, jsonVehicle).commit()
    }

    fun clear(){
        storage.edit().clear().commit()
    }




}