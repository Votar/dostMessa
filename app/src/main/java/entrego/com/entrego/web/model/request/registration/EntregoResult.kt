package entrego.com.entrego.web.model.request.registration

import android.service.voice.AlwaysOnHotwordDetector

/**
 * Created by bertalt on 29.11.16.
 */
class EntregoResult(val code: Int?,
                    val message: String?) {

    override fun toString(): String {
        return "EntregoResult(code=$code, message=$message)"
    }
}