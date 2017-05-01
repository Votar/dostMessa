package com.entregoya.msngr.storage.realm

import com.entregoya.msngr.storage.model.DeliveryModel
import io.realm.Realm
import io.realm.RealmConfiguration


object RealmController : StorageContract {


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

    override fun cacheDeliveryModels(list: Array<DeliveryModel>) {

    }
}