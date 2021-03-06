package com.entregoya.msngr.storage.model

/**
 * Created by bertalt on 29.11.16.
 */
class EntregoPhoneModel(val code: String,
                        val number: String) {

    override fun toString(): String {
        return "EntregoPhoneModel(code='$code', number='$number')"
    }

    fun toView() : String = "$code$number"
}