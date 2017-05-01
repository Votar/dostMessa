package com.entregoya.msngr.ui.score.comments.presenter

import com.entregoya.msngr.entity.CommentEntity
import com.entregoya.msngr.mvp.presenter.BaseMvpPresenter
import com.entregoya.msngr.storage.preferences.EntregoStorage
import com.entregoya.msngr.ui.score.comments.CommentsContract
import com.entregoya.msngr.ui.score.comments.model.CommentsListRequest

class CommentsPresenter : BaseMvpPresenter<CommentsContract.View>(),
        CommentsContract.Presenter {

    val mToken = EntregoStorage.getToken()
    val mResponseListener = object : CommentsListRequest.CommentsListRequestListener {
        override fun onSuccessCommentsListRequest(resultList: Array<CommentEntity>) {
            if (resultList.isNotEmpty())
                mView?.showList(resultList)
            else
                mView?.showEmptyView()
        }

        override fun onFailureCommentsListRequest(code: Int?, message: String?) {
            mView?.showEmptyView()
            mView?.showError(message)
        }
    }

    override fun attachView(view: CommentsContract.View) {
        super.attachView(view)
        requestCommentsList()
    }

    override fun requestCommentsList() {
        CommentsListRequest().executeAsync(mToken, mResponseListener)
    }

}
