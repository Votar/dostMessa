package com.entregoya.msngr.ui.score.comments

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.entregoya.msngr.R
import com.entregoya.msngr.entity.CommentEntity
import com.entregoya.msngr.mvp.view.BaseMvpActivity
import com.entregoya.msngr.ui.score.comments.presenter.CommentsPresenter
import kotlinx.android.synthetic.main.activity_comments.*
import kotlinx.android.synthetic.main.navigation_toolbar.*

class CommentsActivity : BaseMvpActivity<CommentsContract.View, CommentsContract.Presenter>(),
        CommentsContract.View {
    override fun getRootView(): View? = activity_comments
    override var mPresenter: CommentsContract.Presenter = CommentsPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comments)
        setSupportActionBar(navigation_toolbar)
    }

    override fun onStart() {
        super.onStart()
        nav_toolbar_back.setOnClickListener { NavUtils.navigateUpFromSameTask(this) }
        comments_recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    override fun showList(resultList: Array<CommentEntity>) {
        comments_progress.visibility = View.GONE
        comments_recycler.adapter = CommentsAdapter(resultList.toList())
    }

    override fun showEmptyView() {
        comments_progress.visibility = View.GONE

    }


}
