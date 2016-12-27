package entrego.com.android.ui.account.help.reports.create

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NavUtils
import com.google.gson.Gson
import entrego.com.android.R
import entrego.com.android.databinding.ActivityReportSendMessageBinding
import entrego.com.android.ui.account.help.reports.model.ReportModel
import kotlinx.android.synthetic.main.navigation_toolbar.*
import java.util.*

class ReportSendMessageActivity : AppCompatActivity() {

    companion object {
        val EXT_KEY_REPORT = "ext_key_report"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binder: ActivityReportSendMessageBinding = DataBindingUtil.setContentView(this, R.layout.activity_report_send_message)
        nav_toolbar_back.setOnClickListener { NavUtils.navigateUpFromSameTask(this) }
        if (intent != null) {
            if (!intent.hasExtra(EXT_KEY_REPORT)) {
                val report = ReportModel(0, "Create report", "Creating", "Service","","",Calendar.getInstance().time.toString())
                binder.report = report
            } else {
                val jsonReport = intent.getStringExtra(EXT_KEY_REPORT)
                val report = Gson().fromJson(jsonReport, ReportModel::class.java)
                binder.report = report
            }
        }
    }


}
