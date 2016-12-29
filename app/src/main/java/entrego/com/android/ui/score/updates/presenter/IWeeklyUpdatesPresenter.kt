package entrego.com.android.ui.score.updates.presenter

import entrego.com.android.ui.score.updates.view.IWeeklyUpdatesView

/**
 * Created by bertalt on 28.12.16.
 */
interface IWeeklyUpdatesPresenter {
    fun onCreate(view: IWeeklyUpdatesView)
    fun onDestroy()
}