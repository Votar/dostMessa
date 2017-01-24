package entrego.com.android.ui.incomes.history

import android.app.ProgressDialog
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
import entrego.com.android.storage.model.DeliveryModel
import entrego.com.android.storage.preferences.EntregoStorage
import entrego.com.android.ui.faq.HistoryServiceAdapter
import entrego.com.android.ui.incomes.history.details.DetailsOfServiceActivity
import entrego.com.android.ui.incomes.history.presenter.HistoryServicePresenter
import entrego.com.android.ui.incomes.history.presenter.IHistoryServicePresenter
import entrego.com.android.ui.incomes.history.view.IHistoryServiceView
import entrego.com.android.util.loading
import kotlinx.android.synthetic.main.activity_service_history.*
import kotlinx.android.synthetic.main.navigation_toolbar.*
import org.joda.time.DateTime

class HistoryServiceActivity : AppCompatActivity(), IHistoryServiceView {

    companion object {
        val KEY_FROM = "ext_key_from"
        val KEY_TO = "ext_key_to"
    }

    var mProgress: ProgressDialog? = null
    val mPresenter: IHistoryServicePresenter = HistoryServicePresenter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_history)
        setSupportActionBar(navigation_toolbar)
    }

    override fun onStart() {
        super.onStart()

        val token = EntregoStorage(this).getToken()
        if (intent.hasExtra(KEY_FROM) && intent.hasExtra(KEY_TO)) {
            val from = intent.getLongExtra(KEY_FROM, DateTime.now().toLocalDateTime().toDate().time)
            val to = intent.getLongExtra(KEY_TO, DateTime.now().toLocalDateTime().toDate().time)
            mPresenter.onCreate(this, token, Pair(from, to))
        }
        nav_toolbar_back.setOnClickListener { NavUtils.navigateUpFromSameTask(this) }
        val dividerItemDecoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        mProgress = ProgressDialog(this)
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
            mPresenter.requestServiceDetailsById(item.id)
        }
    }

    override fun showProgress() {
        mProgress?.loading()
    }

    override fun hideProgress() {
        mProgress?.dismiss()
    }

    override fun showDetailsHistoryService(model: DeliveryModel) {
        val intent = DetailsOfServiceActivity.getIntent(this, model)
        startActivity(intent)
    }

}
