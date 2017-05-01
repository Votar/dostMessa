package com.entregoya.msngr.storage.realm.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
open class EntregoService constructor() : RealmObject() {

    @PrimaryKey var id: Long = 0
    var title = ""
    constructor(id: Long, title: String) : this() {
        this.id = id
        this.title = title
    }
}