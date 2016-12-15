package entrego.com.android

import android.app.Application
import android.content.Intent
import entrego.com.android.storage.preferences.EntregoStorage
import entrego.com.android.storage.realm.RealmController
import entrego.com.android.ui.auth.AuthActivity
import entrego.com.android.util.Logger
import entrego.com.android.util.event_bus.LogoutEvent
import entrego.com.android.ui.main.account.vehicle.edit.model.UserVehicle
import io.realm.Realm
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


/**
 * Created by bertalt on 30.11.16.
 */
class EntregoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Logger.DEBUG = true

        UserVehicle.refresh(applicationContext, null)

        Realm.init(this)
    }


}