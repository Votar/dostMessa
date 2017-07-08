package com.entregoya.msngr

import android.support.multidex.MultiDexApplication
import android.support.v7.app.AppCompatDelegate
import com.crashlytics.android.Crashlytics
import com.entregoya.msngr.util.Logger
import com.entregoya.msngr.storage.preferences.EntregoStorage
import com.entregoya.msngr.ui.account.vehicle.edit.model.UserVehicle
import io.fabric.sdk.android.Fabric
import io.realm.Realm
import net.danlew.android.joda.JodaTimeAndroid

class EntregoApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        Logger.DEBUG = true
        EntregoStorage.init(this)
        Fabric.with(this, Crashlytics())
        UserVehicle.refresh(applicationContext, null)
        JodaTimeAndroid.init(this)
        Realm.init(this)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }
}