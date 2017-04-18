package com.entregoya.msngr.ui.account.help.reports.create

import android.app.ProgressDialog
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.view.View
import com.bumptech.glide.Glide
import com.entregoya.msngr.R
import com.entregoya.msngr.binding.Delivery
import com.entregoya.msngr.databinding.ActivityReportSendMessageBinding
import com.entregoya.msngr.mvp.view.BaseMvpActivity
import com.entregoya.msngr.storage.preferences.EntregoStorage
import com.entregoya.msngr.util.formattedDefaultDate
import com.entregoya.msngr.util.loadMessengerPhoto
import com.entregoya.msngr.util.loading
import com.entregoya.msngr.web.api.EntregoApi
import kotlinx.android.synthetic.main.activity_report_send_message.*
import kotlinx.android.synthetic.main.navigation_toolbar.*
import java.util.*

class ReportSendMessageActivity : BaseMvpActivity<SendReportContract.View,
        SendReportContract.Presenter>(),
        SendReportContract.View {

    override fun getRootView(): View? = activity_report_send
    override var mPresenter: SendReportContract.Presenter = SendReportPresenter()

    companion object {
        val EXT_KEY_REPORT = "ext_key_report"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binder: ActivityReportSendMessageBinding = DataBindingUtil.setContentView(this, R.layout.activity_report_send_message)
        val profile = EntregoStorage.getUserProfile()
        binder.profile = EntregoStorage.getUserProfile()
        setupLayouts()
    }

    var mProgress: ProgressDialog? = null
    override fun showProgress() {
        mProgress = ProgressDialog(this)
        mProgress?.loading()
    }

    override fun hideProgress() {
        mProgress?.dismiss()
    }


    fun setupLayouts() {
        nav_toolbar_back.setOnClickListener { NavUtils.navigateUpFromSameTask(this) }
        report_create_send_btn.setOnClickListener {
            mPresenter.sendReport(report_create_edit_text.text.toString())
        }
        report_send_timestamp.text = Calendar.getInstance().time.time.formattedDefaultDate()
        report_create_user_photo.loadMessengerPhoto()
        report_send_order_id.text = Delivery.getInstance().id.toString()
    }
}
