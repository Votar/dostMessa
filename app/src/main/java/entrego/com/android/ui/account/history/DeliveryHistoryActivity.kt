package entrego.com.android.ui.account.history

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import entrego.com.android.R
import entrego.com.android.storage.preferences.EntregoStorage
import entrego.com.android.ui.account.history.model.DeliveryHistoryAdapter
import entrego.com.android.ui.account.history.presenter.DeliveryHistoryPresenter
import entrego.com.android.ui.account.history.presenter.IDeliveryHistoryPresenter
import entrego.com.android.ui.account.history.view.IDeliveryHistoryView
import entrego.com.android.util.UserMessageUtil
import entrego.com.android.util.loading
import kotlinx.android.synthetic.main.activity_delivery_history.*
import kotlinx.android.synthetic.main.navigation_toolbar.*

class DeliveryHistoryActivity : AppCompatActivity(), IDeliveryHistoryView {

    val mPresenter: IDeliveryHistoryPresenter = DeliveryHistoryPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery_history)
        del_hist_recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        setSupportActionBar(navigation_toolbar)
        nav_toolbar_back.setOnClickListener { onBackPressed() }
        mPresenter.onCreate(this)
        val token = EntregoStorage(this).getToken()
        mPresenter.requestHistoryList(token)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroy()
    }


    override fun showEmptyView() {
        del_hist_progress.visibility = View.VISIBLE
        del_hist_recycler.visibility = View.GONE
    }

    override fun showHistoryList() {
        del_hist_progress.visibility = View.GONE
        del_hist_recycler.visibility = View.VISIBLE

        del_hist_recycler.adapter = DeliveryHistoryAdapter(this)

    }

    var progressFragment: ProgressDialog? = null
    override fun showProgress() {
        progressFragment = ProgressDialog(this)
        progressFragment?.loading()
    }

    override fun hideProgress() {
        progressFragment?.hide()
    }

    override fun showMessage(message: String?) {

        UserMessageUtil.show(this, message)
    }

    override fun getAppContext(): Context {
        return applicationContext
    }

}
