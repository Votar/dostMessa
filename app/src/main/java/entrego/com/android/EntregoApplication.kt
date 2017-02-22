package entrego.com.android

import android.support.multidex.MultiDexApplication
import com.crashlytics.android.Crashlytics
import entrego.com.android.util.Logger
import entrego.com.android.storage.preferences.EntregoStorage
import entrego.com.android.ui.account.vehicle.edit.model.UserVehicle
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
    }
}