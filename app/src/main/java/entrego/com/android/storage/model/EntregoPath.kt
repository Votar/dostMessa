package entrego.com.android.storage.model

import java.util.*


class EntregoPath(val line: String,
                  val duration: Long,
                  val distance: Double) {

    fun getDurationInMinutes(): Long {
        return duration.div(60)
    }

    fun getDurationInHours(): Float {
        return getDurationInMinutes().div(60).toFloat()
    }

    fun getDistanceInKmString(): String {
        return java.lang.String.format(Locale.getDefault(), "%1$.2f", (distance / 1000))
    }

    fun getDurationInMinutesString(): String {
        return getDurationInMinutes().toString()
    }

}