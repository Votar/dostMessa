package entrego.com.android.ui.score.updates.model

import android.os.Handler

/**
 * Created by bertalt on 28.12.16.
 */
object WeeklyUpdatesController {

    interface WeeklyUpdatesListener {
        fun onSuccessGetUpdates(list: List<RatingModel>)
        fun onFailureGetUpdates(code: Int?, message: String?)
    }

    fun getActualUpdatesAsync(listener: WeeklyUpdatesListener?) {

        Handler().postDelayed({
            //TODO:Remove mocked data
            val mock = listOf(
                    RatingModel("Rating list title", 4.2F),
                    RatingModel("Rating list title", 3.2F),
                    RatingModel("Rating list title", 2F)
            )

            listener?.onSuccessGetUpdates(mock)
        }, 1500)

    }
}