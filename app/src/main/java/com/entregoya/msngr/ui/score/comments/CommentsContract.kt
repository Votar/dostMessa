package com.entregoya.msngr.ui.score.comments

import com.entregoya.msngr.entity.CommentEntity
import com.entregoya.msngr.mvp.presenter.IBaseMvpPresenter
import com.entregoya.msngr.mvp.view.IBaseMvpView

interface CommentsContract {
    interface View : IBaseMvpView {
        fun showList(resultList: Array<CommentEntity>)
        fun showEmptyView()
    }

    interface Presenter : IBaseMvpPresenter<View> {
        fun requestCommentsList()
    }
}