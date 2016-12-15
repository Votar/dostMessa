package entrego.com.android.storage.realm

import entrego.com.android.util.Logger
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * Created by bertalt on 15.12.16.
 */
object RealmController {

    private val DB_NAME: String = "entrego_db"

    private val SCHEMA_VERSION: Long = 1

    init {

        val realmConfiguration = RealmConfiguration.Builder()
                .name(DB_NAME)
                .schemaVersion(SCHEMA_VERSION)
                .deleteRealmIfMigrationNeeded()
                .build()

        Realm.setDefaultConfiguration(realmConfiguration)
    }

}