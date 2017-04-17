package com.entregoya.msngr.ui.score.tips.presenter

import com.entregoya.msngr.ui.score.tips.model.TipsController
import com.entregoya.msngr.ui.score.tips.view.ITipsView

/**
 * Created by bertalt on 29.12.16.
 */
class TipsPresenter : ITipsPresenter {

    var mView: ITipsView? = null
    override fun onCreate(view: ITipsView) {
        mView = view
        TipsController.requestTipsAsync(getTipsListener)
    }

    override fun onDestroy() {
        mView = null
    }

    val getTipsListener = object : TipsController.GetTipsListener {
        override fun onSuccessGetTips(list: List<String>) {
            mView?.buildView(list)
        }

        override fun onFailureGetTips(code: Int?, message: String?) {
            mView?.showEmptyView(message)
        }

    }
}