package com.entregoya.msngr.web.model.response

import android.service.voice.AlwaysOnHotwordDetector

open class EntregoResult(open val code: Int?,
                         open val message: String?) {

    override fun toString(): String {
        return "EntregoResult(code=$code, message=$message)"
    }
}