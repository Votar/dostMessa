package com.entregoya.msngr.ui.score.updates.presenter

import com.entregoya.msngr.ui.score.updates.model.RatingModel
import com.entregoya.msngr.ui.score.updates.model.WeeklyUpdatesController
import com.entregoya.msngr.ui.score.updates.view.IWeeklyUpdatesView

/**
 * Created by bertalt on 28.12.16.
 */
class WeeklyUpdatesPresenter : IWeeklyUpdatesPresenter {

    var mView: IWeeklyUpdatesView? = null


    override fun onCreate(view: IWeeklyUpdatesView) {
        mView = view
        WeeklyUpdatesController.getActualUpdatesAsync(mGetUpdatesListener)
    }

    override fun onDestroy() {
        mView = null
    }

    val mGetUpdatesListener = object : WeeklyUpdatesController.WeeklyUpdatesListener {
        override fun onSuccessGetUpdates(list: List<RatingModel>) {
            mView?.buildView(list)
        }

        override fun onFailureGetUpdates(code: Int?, message: String?) {
            mView?.showEmptyView(message)
        }
    }
}