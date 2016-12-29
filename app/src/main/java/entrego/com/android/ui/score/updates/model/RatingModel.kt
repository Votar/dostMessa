package entrego.com.android.ui.score.updates.model

import entrego.com.android.util.formatRating
import java.util.*

/**
 * Created by bertalt on 28.12.16.
 */
class RatingModel(val title: String,
                  val rating: Float) {

    fun ratingView(): String {
        return rating.formatRating(rating)
    }
}