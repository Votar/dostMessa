package entrego.com.android.ui.score.updates.model

import entrego.com.android.util.formatRating
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import java.util.*

/**
 * Created by bertalt on 28.12.16.
 */
class RatingModel(val timestamp: DateTime,
                  val rating: Float) {

    fun ratingView(): String {
        return rating.formatRating(rating)
    }

    fun getTimestampView(): String {
        return DateTimeFormat.forPattern("yyyy-MM-dd hh:MM").print(timestamp)
    }
}