package entrego.com.android

import android.app.Application
import com.crashlytics.android.Crashlytics
import entrego.com.android.ui.account.vehicle.edit.model.UserVehicle
import entrego.com.android.util.Logger
import entrego.com.android.web.model.AccessToken
import io.fabric.sdk.android.Fabric
import io.realm.Realm
import net.danlew.android.joda.JodaTimeAndroid

class EntregoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Logger.DEBUG = true
        AccessToken.init(this)
        Fabric.with(this, Crashlytics())
        UserVehicle.refresh(applicationContext, null)
        JodaTimeAndroid.init(this)
        Realm.init(this)

    }

}