package entrego.com.android

import android.app.Application
import android.content.Intent
import entrego.com.android.web.socket.SocketService
import entrego.com.android.ui.account.vehicle.edit.model.UserVehicle
import entrego.com.android.util.Logger
import io.realm.Realm

class EntregoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Logger.DEBUG = true

        UserVehicle.refresh(applicationContext, null)

        Realm.init(this)

    }

}