package com.entregoya.msngr.ui.account.history

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.entregoya.msngr.R
import com.entregoya.msngr.storage.model.DeliveryModel
import com.entregoya.msngr.storage.preferences.EntregoStorage
import com.entregoya.msngr.ui.account.history.details.RouteHistoryDetailsActivity
import com.entregoya.msngr.ui.account.history.model.DeliveryHistoryAdapter
import com.entregoya.msngr.ui.account.history.presenter.DeliveryHistoryPresenter
import com.entregoya.msngr.ui.account.history.presenter.IDeliveryHistoryPresenter
import com.entregoya.msngr.ui.account.history.view.IDeliveryHistoryView
import com.entregoya.msngr.util.UserMessageUtil
import kotlinx.android.synthetic.main.activity_delivery_history.*
import kotlinx.android.synthetic.main.include_empty_view.*
import kotlinx.android.synthetic.main.navigation_toolbar.*

class DeliveryHistoryActivity : AppCompatActivity(), IDeliveryHistoryView {

    val mPresenter: IDeliveryHistoryPresenter = DeliveryHistoryPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery_history)
        mPresenter.onCreate(this)
    }

    override fun onStart() {
        super.onStart()
        del_hist_recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        setSupportActionBar(navigation_toolbar)
        nav_toolbar_back.setOnClickListener { onBackPressed() }
        val token = EntregoStorage.getToken()
        mPresenter.requestHistoryList(token)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroy()
    }

    override fun showEmptyView() {
        del_hist_progress.visibility = View.GONE
        del_hist_recycler.visibility = View.GONE
        common_empty_view?.visibility = View.VISIBLE
    }

    override fun showHistoryList(resultList: Array<DeliveryModel>) {
        common_empty_view?.visibility = View.GONE
        del_hist_progress.visibility = View.GONE
        del_hist_recycler.visibility = View.VISIBLE
        del_hist_recycler.adapter = DeliveryHistoryAdapter(resultList, onItemClickListener)

    }

    override fun showMessage(message: String?) {
        UserMessageUtil.showSnackMessage(activity_delivery_history, message)
    }

    override fun getAppContext(): Context {
        return applicationContext
    }

    val onItemClickListener = object : DeliveryHistoryAdapter.ClickItemListener {
        override fun onItemClicked(delivery: DeliveryModel) {
            RouteHistoryDetailsActivity.start(del_hist_recycler.context, delivery)
        }
    }
}
