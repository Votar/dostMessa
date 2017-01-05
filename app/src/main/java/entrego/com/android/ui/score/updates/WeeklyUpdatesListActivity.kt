package entrego.com.android.ui.score.updates

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import entrego.com.android.R
import entrego.com.android.ui.faq.UpdatesAdapter
import entrego.com.android.ui.score.updates.details.UpdatesDetailsActivity
import entrego.com.android.ui.score.updates.model.RatingModel
import entrego.com.android.ui.score.updates.presenter.IWeeklyUpdatesPresenter
import entrego.com.android.ui.score.updates.presenter.WeeklyUpdatesPresenter
import entrego.com.android.ui.score.updates.view.IWeeklyUpdatesView
import entrego.com.android.util.UserMessageUtil
import kotlinx.android.synthetic.main.activity_weekly_updates_list.*
import kotlinx.android.synthetic.main.include_empty_view.*
import kotlinx.android.synthetic.main.navigation_toolbar.*

class WeeklyUpdatesListActivity : AppCompatActivity(), IWeeklyUpdatesView {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, WeeklyUpdatesListActivity::class.java)
            context.startActivity(intent)
        }
    }


    val mPresenter: IWeeklyUpdatesPresenter = WeeklyUpdatesPresenter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weekly_updates_list)
        nav_toolbar_back.setOnClickListener { NavUtils.navigateUpFromSameTask(this) }
        weekly_updates_recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val dividerItemDecoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        weekly_updates_recycler.addItemDecoration(dividerItemDecoration)
        mPresenter.onCreate(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroy()
    }

    override fun showMessage(message: String?) {
        UserMessageUtil.showSnackMessage(activity_weekly_updates_list, message)
    }

    override fun showEmptyView(message: String?) {
        weekly_updates_progress.visibility = View.GONE
        weekly_updates_recycler.visibility = View.GONE
        weekly_updates_empty.visibility = View.VISIBLE

        if (message != null)
            common_empty_text.text = message
    }

    override fun buildView(list: List<RatingModel>) {
        weekly_updates_progress.visibility = View.GONE
        weekly_updates_recycler.visibility = View.VISIBLE
        weekly_updates_scroll.visibility = View.VISIBLE
        weekly_updates_recycler.adapter = UpdatesAdapter(list, mClickItemListener)
    }

    val mClickItemListener = object : UpdatesAdapter.RatingClickListener {
        override fun onItemClicked(item: RatingModel) {
            UpdatesDetailsActivity.start(weekly_updates_scroll.context)
        }
    }

}
