package entrego.com.entrego

import android.app.Application
import entrego.com.entrego.util.Logger

/**
 * Created by bertalt on 30.11.16.
 */
class EntregoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Logger.DEBUG = true
    }
}