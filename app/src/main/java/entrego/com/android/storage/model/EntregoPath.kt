package entrego.com.android.storage.model

/**
 * Created by bertalt on 27.12.16.
 */
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
        return (distance / 1000).toString()
    }

    fun getDurationInMinutesString(): String {
        return getDurationInMinutes().toString()
    }

}