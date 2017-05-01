package com.entregoya.msngr.storage.preferences

import android.content.Context
import android.content.SharedPreferences
import com.entregoya.msngr.storage.model.UserProfileModel
import com.entregoya.msngr.storage.model.UserVehicleModel
import com.entregoya.msngr.ui.account.profile.account.model.AccountEntity
import com.entregoya.msngr.util.GsonHolder
import com.entregoya.msngr.util.event_bus.LogoutEvent
import org.greenrobot.eventbus.EventBus

object EntregoStorage {

    private lateinit var storage: SharedPreferences
    private val KEY_TOKEN = "pref_key_token"
    private val KEY_LAST_EMAIL = "pref_key_email"
    private val KEY_USER_PROFILE = "pref_key_user_profile"
    private val KEY_USER_VEHICLE = "pref_key_user_vehicle"
    private val PREF_STORAGE_NAME = "etrego_pref_store"
    private val KEY_BANK_ACCOUNT = "pref_k_bank_account"

    fun init(context: Context) {
        storage = context.getSharedPreferences(PREF_STORAGE_NAME, Context.MODE_PRIVATE)
    }

    fun getToken(): String {
        val token = storage.getString(KEY_TOKEN, "")
        if (token.isEmpty())
            EventBus.getDefault().post(LogoutEvent())
        return token
    }

    fun setToken(token: String?) {
        if (token != null) {
            storage.edit().putString(KEY_TOKEN, token).commit()
        }
    }

    fun setUserProfile(profile: UserProfileModel?) {
        profile?.let {
            val jsonProfile = GsonHolder.instance.toJson(it, UserProfileModel::class.java)
            storage.edit().putString(KEY_USER_PROFILE, jsonProfile).commit()
        }
    }

    fun getUserProfile(): UserProfileModel? {

        val jsonProfile = storage.getString(KEY_USER_PROFILE, "")

        if (jsonProfile.isEmpty())
            return null
        else
            return GsonHolder.instance.fromJson(jsonProfile, UserProfileModel::class.java)

    }

    fun getUserVehicle(): UserVehicleModel? {

        val jsonProfile = storage.getString(KEY_USER_VEHICLE, "")

        if (jsonProfile.isEmpty())
            return null
        else
            return GsonHolder.instance.fromJson(jsonProfile, UserVehicleModel::class.java)

    }

    fun setUserVehicle(vehicle: UserVehicleModel?) {
        val jsonVehicle = GsonHolder.instance.toJson(vehicle, UserVehicleModel::class.java)
        storage.edit().putString(KEY_USER_VEHICLE, jsonVehicle).commit()
    }

    fun clear() {
        val lastEmail = getLastEmail()
        storage.edit().clear().commit()
        storage.edit().putString(KEY_LAST_EMAIL, lastEmail).apply()
    }

    fun getLastEmail(): String = storage.getString(KEY_LAST_EMAIL, "")

    fun setLastEmail(email: String) {
        storage.edit().putString(KEY_LAST_EMAIL, email).apply()
    }

    fun saveBankAccount(account: AccountEntity): Boolean {
        val serialize = GsonHolder.instance.toJson(account, AccountEntity::class.java)
        return storage.edit().putString(KEY_BANK_ACCOUNT, serialize).commit()
    }

    fun getBankAccount(): AccountEntity? {
        val json = storage.getString(KEY_BANK_ACCOUNT, "")
        return GsonHolder.instance.fromJson(json, AccountEntity::class.java)
    }


}