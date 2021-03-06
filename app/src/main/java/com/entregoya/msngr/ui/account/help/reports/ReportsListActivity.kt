package com.entregoya.msngr.ui.account.help.reports

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.google.gson.Gson
import com.entregoya.msngr.R
import com.entregoya.msngr.ui.account.help.reports.create.ReportSendMessageActivity
import com.entregoya.msngr.ui.account.help.reports.details.ReportDetailsActivity
import com.entregoya.msngr.ui.account.help.reports.model.ReportEntity
import com.entregoya.msngr.ui.account.help.reports.presenter.IReportsListPresenter
import com.entregoya.msngr.ui.account.help.reports.presenter.ReportsListPresenter
import com.entregoya.msngr.ui.account.help.reports.view.IReportsListView
import com.entregoya.msngr.ui.account.help.reports.model.ReportsAdapter
import com.entregoya.msngr.util.GsonHolder
import com.entregoya.msngr.util.UserMessageUtil
import kotlinx.android.synthetic.main.activity_reports_list.*
import kotlinx.android.synthetic.main.navigation_toolbar.*

class ReportsListActivity : AppCompatActivity(), IReportsListView {

    val mPresenter: IReportsListPresenter = ReportsListPresenter()

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reports_list)
            setSupportActionBar(navigation_toolbar)
            mPresenter.onCreate(this)
    }

    override fun onStart() {
        super.onStart()
        reports_list_recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val dividerItemDecoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        reports_list_recycler.addItemDecoration(dividerItemDecoration)
        nav_toolbar_back.setOnClickListener { NavUtils.navigateUpFromSameTask(this) }
        reports_fab_add_report.setOnClickListener { startNewReportActivity() }
    }

    val onReportClickListener = object : ReportsAdapter.ReportClickListener {
        override fun onReportClicked(item: ReportEntity) {
            val intent = Intent(applicationContext, ReportDetailsActivity::class.java)
            val json = GsonHolder.instance.toJson(item, ReportEntity::class.java)
            intent.putExtra(ReportDetailsActivity.EXT_KEY_REPORT, json)
            startActivity(intent)
        }
    }

    override fun buildView(reports: List<ReportEntity>) {
        reports_list_progress.visibility = View.GONE
        reports_list_scroll.visibility = View.VISIBLE
        reports_list_recycler.adapter = ReportsAdapter(reports, onReportClickListener)
    }

    override fun showEmptyView() {
        reports_list_progress.visibility = View.GONE
    }

    override fun showMessage(message: String?) {
        UserMessageUtil.showSnackMessage(activity_reports_list, message)
    }

    private fun startNewReportActivity() {
        val intent = Intent(this, ReportSendMessageActivity::class.java)
        startActivity(intent)
    }


}
