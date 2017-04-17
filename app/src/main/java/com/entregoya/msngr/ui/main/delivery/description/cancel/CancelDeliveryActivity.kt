package com.entregoya.msngr.ui.main.delivery.description.cancel

import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AlertDialog
import com.entregoya.msngr.R
import com.entregoya.msngr.ui.main.delivery.description.cancel.presenter.CancelDeliveryPresenter
import com.entregoya.msngr.ui.main.delivery.description.cancel.presenter.ICancelDeliveryPresenter
import com.entregoya.msngr.ui.main.delivery.description.cancel.view.ICancelDeliveryView
import com.entregoya.msngr.util.UserMessageUtil
import com.entregoya.msngr.util.loading
import kotlinx.android.synthetic.main.activity_cancel_delivery.*
import kotlinx.android.synthetic.main.navigation_toolbar.*
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager


class CancelDeliveryActivity : AppCompatActivity(), ICancelDeliveryView {

    val mPresenter: ICancelDeliveryPresenter = CancelDeliveryPresenter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cancel_delivery)
        mPresenter.onCreate(this)
        //setup mView
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val dividerItemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        cancel_delivery_recycler_reasons.addItemDecoration(dividerItemDecoration)
        cancel_delivery_recycler_reasons.layoutManager = layoutManager
        // setup adapter
        val reasons = resources.getStringArray(R.array.cancel_reasons).toList()
        mPresenter.setupRecyclerView(cancel_delivery_recycler_reasons, reasons)
        setSupportActionBar(navigation_toolbar)
        nav_toolbar_back.setOnClickListener { NavUtils.navigateUpFromSameTask(this) }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroy()
    }

    var loadingDialog: ProgressDialog? = null
    override fun onShowProgress() {
        loadingDialog = ProgressDialog(this)
        loadingDialog?.loading()
    }

    override fun onHideProgress() {
        loadingDialog?.dismiss()
    }

    override fun showMessage(message: String?) {
        UserMessageUtil.showSnackMessage(activity_cancel_delivery, message)
    }

    override fun getActivityContext(): Context {
        return this
    }

    override fun showSuccessScreen() {
        startActivity(Intent(this, SuccessCancelationActivity::class.java))
    }


}
