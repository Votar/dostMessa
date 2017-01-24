package entrego.com.android.ui.score.updates.model

import android.os.Handler
import org.joda.time.DateTime

object WeeklyUpdatesController {

    interface WeeklyUpdatesListener {
        fun onSuccessGetUpdates(list: List<RatingModel>)
        fun onFailureGetUpdates(code: Int?, message: String?)
    }

    fun getActualUpdatesAsync(listener: WeeklyUpdatesListener?) {

        Handler().postDelayed({
            //TODO:Remove mocked data
            val mock = listOf(
                    RatingModel(DateTime(1485093543000L), 4.2F),
                    RatingModel(DateTime(1482093543000L), 3.2F),
                    RatingModel(DateTime(1482013143000L), 2F)
            )

            listener?.onSuccessGetUpdates(mock)
        }, 1500)

    }
}