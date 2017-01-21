package entrego.com.android.ui.incomes.history

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import entrego.com.android.R
import entrego.com.android.binding.HistoryServiceBinding
import entrego.com.android.entity.HistoryServicesPreviewEntity
import entrego.com.android.storage.preferences.EntregoStorage
import entrego.com.android.ui.faq.HistoryServiceAdapter
import entrego.com.android.ui.incomes.history.details.DetailsOfServiceActivity
import entrego.com.android.ui.incomes.history.presenter.HistoryServicePresenter
import entrego.com.android.ui.incomes.history.presenter.IHistoryServicePresenter
import entrego.com.android.ui.incomes.history.view.IHistoryServiceView
import kotlinx.android.synthetic.main.activity_service_history.*
import kotlinx.android.synthetic.main.navigation_toolbar.*

class HistoryServiceActivity : AppCompatActivity(), IHistoryServiceView {

    val mPresenter: IHistoryServicePresenter = HistoryServicePresenter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_history)
        val token = EntregoStorage(this).getToken()
        mPresenter.onCreate(this,token)

        setSupportActionBar(navigation_toolbar)
        nav_toolbar_back.setOnClickListener { NavUtils.navigateUpFromSameTask(this) }

        val dividerItemDecoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)

        history_service_recent_recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        history_service_recent_recycler.addItemDecoration(dividerItemDecoration)
        history_service_today_recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        history_service_today_recycler.addItemDecoration(dividerItemDecoration)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroy()
    }

    override fun buildTodayServices(list: List<HistoryServicesPreviewEntity>) {
        history_service_today_progress.visibility = View.GONE
        history_service_today_recycler.adapter = HistoryServiceAdapter(list, onItemClicked)
    }

    override fun buildRecentServices(list: List<HistoryServicesPreviewEntity>) {
        history_service_recent_progress.visibility = View.GONE
        history_service_recent_recycler.adapter = HistoryServiceAdapter(list, onItemClicked)
    }

    override fun showEmptyTodayServicesList() {
        history_service_today_progress.visibility = View.GONE
        history_service_today_empty.visibility = View.VISIBLE
    }

    override fun showEmptyRecentServicesList() {
        history_service_recent_progress.visibility = View.GONE
        history_service_recent_empty.visibility = View.VISIBLE
    }

    val onItemClicked = object : HistoryServiceAdapter.HistoryServiceClickListener {
        override fun onHistoryClicked(item: HistoryServicesPreviewEntity) {

            val intent = Intent(applicationContext, DetailsOfServiceActivity::class.java)
            startActivity(intent)
        }
    }
}
