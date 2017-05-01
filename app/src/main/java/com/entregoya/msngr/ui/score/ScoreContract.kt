package com.entregoya.msngr.ui.score

import com.entregoya.msngr.entity.ScoreEntity
import com.entregoya.msngr.mvp.presenter.IBaseMvpPresenter
import com.entregoya.msngr.mvp.view.IBaseMvpView


interface ScoreContract {
    interface View : IBaseMvpView {
        fun setupScoreView(bestMessenger: ScoreEntity, userScore: ScoreEntity)
    }

    interface Presenter : IBaseMvpPresenter<View>{
        fun requestBestMessenger()
    }
}