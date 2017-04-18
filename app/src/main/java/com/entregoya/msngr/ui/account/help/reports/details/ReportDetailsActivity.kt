package com.entregoya.msngr.ui.account.help.reports.details

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.view.View
import com.google.gson.Gson
import com.entregoya.msngr.R
import com.entregoya.msngr.databinding.ActivityReportDetailsBinding
import com.entregoya.msngr.ui.account.help.reports.model.ReportEntity
import com.entregoya.msngr.ui.account.profile.UserProfile
import com.entregoya.msngr.util.*
import kotlinx.android.synthetic.main.activity_report_details.*
import kotlinx.android.synthetic.main.navigation_toolbar.*
import java.util.*

class ReportDetailsActivity : AppCompatActivity() {
    companion object {
        val EXT_KEY_REPORT = "ext_key_report"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binder: ActivityReportDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_report_details)
        if (intent != null) {
            if (intent.hasExtra(EXT_KEY_REPORT)) {
                val jsonReport = intent.getStringExtra(EXT_KEY_REPORT)
                val report = GsonHolder.instance.fromJson(jsonReport, ReportEntity::class.java)
                binder.report = report

            } else throw IllegalStateException("No report model in intent")
        }
        val profile = UserProfile.getProfile()
        binder.profile = profile
        setupLayouts()
        report_details_user_photo.loadMessengerPhoto()
    }

    fun setupLayouts() {
        nav_toolbar_back.setOnClickListener {
            NavUtils.navigateUpFromSameTask(this@ReportDetailsActivity)
        }


    }

    override fun onStart() {
        super.onStart()
    }
}
