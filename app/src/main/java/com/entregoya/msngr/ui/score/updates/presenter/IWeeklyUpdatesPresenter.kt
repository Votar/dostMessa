package com.entregoya.msngr.ui.score.updates.presenter

import com.entregoya.msngr.ui.score.updates.view.IWeeklyUpdatesView

/**
 * Created by bertalt on 28.12.16.
 */
interface IWeeklyUpdatesPresenter {
    fun onCreate(view: IWeeklyUpdatesView)
    fun onDestroy()
}