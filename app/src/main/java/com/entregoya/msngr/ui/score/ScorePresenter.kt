package com.entregoya.msngr.ui.score

import com.entregoya.msngr.entity.ScoreEntity
import com.entregoya.msngr.mvp.presenter.BaseMvpPresenter
import com.entregoya.msngr.storage.preferences.EntregoStorage
import com.entregoya.msngr.ui.score.model.GetScoreRequest
import com.entregoya.msngr.web.api.ApiContract
import com.entregoya.msngr.web.model.response.statistic.EntregoScoreEntity


class ScorePresenter : BaseMvpPresenter<ScoreContract.View>(),
        ScoreContract.Presenter {
    val mToken = EntregoStorage.getToken()
    val mGetScoreResponseListener = object : GetScoreRequest.GetScoreRequestListener {
        override fun onSuccessGetScoreRequest(response: EntregoScoreEntity) {
            mView?.setupScoreView(response.best, ScoreEntity(
                    response.canceled,
                    response.declined,
                    response.average,
                    response.completed
            ))
        }

        override fun onFailureGetScoreRequest(code: Int?, message: String?) {
            when (code) {
                ApiContract.RESPONSE_INVALID_TOKEN -> mView?.onLogout()
                else -> mView?.showError(message)
            }
        }
    }

    override fun requestBestMessenger() {
        GetScoreRequest().executeAsync(mToken, mGetScoreResponseListener)
    }
}