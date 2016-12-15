package entrego.com.android.storage.realm.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by bertalt on 15.12.16.
 */
open class EntregoService constructor() : RealmObject() {

    @PrimaryKey var id: Long = 0
    var title = ""
    constructor(id: Long, title: String) : this() {
        this.id = id
        this.title = title
    }
}