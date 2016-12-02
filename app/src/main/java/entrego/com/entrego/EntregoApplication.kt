package entrego.com.entrego

import android.app.Application
import android.content.Intent
import entrego.com.entrego.storage.preferences.EntregoStorage
import entrego.com.entrego.ui.auth.AuthActivity
import entrego.com.entrego.util.Logger
import entrego.com.entrego.util.event_bus.LogoutEvent
import entrego.com.entrego.ui.main.account.vehicle.edit.model.UserVehicle
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

        EventBus.getDefault().register(this)
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLogoutEvent(event: LogoutEvent) {

        EntregoStorage(this).setToken("")
        val intent = Intent(this, AuthActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)

    }
}