package com.entregoya.msngr.ui.score.updates.view

import com.entregoya.msngr.ui.score.updates.model.RatingModel

/**
 * Created by bertalt on 28.12.16.
 */
interface IWeeklyUpdatesView {
    fun showMessage(message:String?)
    fun showEmptyView(message: String?)
    fun buildView(list : List<RatingModel>)
}