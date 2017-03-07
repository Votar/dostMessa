package entrego.com.android.storage.realm

import entrego.com.android.util.Logger
import io.realm.Realm
import io.realm.RealmConfiguration


object RealmController : StorageContract{


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

    override fun addFavoritePlace(address: String): Boolean {

        return false
    }

    override fun getFavoritesList(): List<String> {


        return emptyList()
    }

    override fun removeFavorite(address: String): Boolean {

        return false

    }

}