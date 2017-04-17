package com.entregoya.msngr.ui.score.tips

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.entregoya.msngr.R
import com.entregoya.msngr.ui.faq.TipsAdapter
import com.entregoya.msngr.ui.score.tips.presenter.ITipsPresenter
import com.entregoya.msngr.ui.score.tips.presenter.TipsPresenter
import com.entregoya.msngr.ui.score.tips.view.ITipsView
import com.entregoya.msngr.ui.score.updates.WeeklyUpdatesListActivity
import com.entregoya.msngr.util.UserMessageUtil
import kotlinx.android.synthetic.main.activity_improvment_tips.*
import kotlinx.android.synthetic.main.navigation_toolbar.*

class ImprovementTipsActivity : AppCompatActivity(), ITipsView {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, ImprovementTipsActivity::class.java)
            context.startActivity(intent)
        }
    }

    val mPresenter: ITipsPresenter = TipsPresenter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_improvment_tips)
        mPresenter.onCreate(this)
    }

    override fun onStart() {
        super.onStart()
        nav_toolbar_back.setOnClickListener { NavUtils.navigateUpFromSameTask(this) }
        tips_recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val dividerItemDecoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        tips_recycler.addItemDecoration(dividerItemDecoration)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroy()
    }

    override fun showMessage(message: String?) {
        UserMessageUtil.showSnackMessage(activity_improvment_tips, message)
    }

    override fun showEmptyView(message: String?) {
        tips_progress_view.visibility = View.GONE
        tips_scroll.visibility = View.GONE
        tips_empty_view.visibility = View.VISIBLE
    }

    override fun buildView(list: List<String>) {
        tips_progress_view.visibility = View.GONE
        tips_scroll.visibility = View.VISIBLE
        tips_empty_view.visibility = View.GONE
        tips_recycler.adapter = TipsAdapter(list)
    }




}
