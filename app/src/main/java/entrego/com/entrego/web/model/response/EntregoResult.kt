package entrego.com.entrego.web.model.response

import android.service.voice.AlwaysOnHotwordDetector

/**
 * Created by bertalt on 29.11.16.
 */
open class EntregoResult(open val code: Int?,
                         open val message: String?) {

    override fun toString(): String {
        return "EntregoResult(code=$code, message=$message)"
    }
}