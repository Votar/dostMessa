package entrego.com.android.ui.account.help.reports.details

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.view.View
import com.google.gson.Gson
import entrego.com.android.R
import entrego.com.android.databinding.ActivityReportDetailsBinding
import entrego.com.android.ui.account.help.reports.model.ReportEntity
import entrego.com.android.ui.account.profile.UserProfile
import entrego.com.android.util.GsonHolder
import entrego.com.android.util.Logger
import entrego.com.android.util.loadSimple
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
            if (!intent.hasExtra(EXT_KEY_REPORT)) {
                val report = ReportEntity(0, "Create report", "Creating", "Service", "", "", Calendar.getInstance().time.toString())
                binder.report = report
            } else {
                val jsonReport = intent.getStringExtra(EXT_KEY_REPORT)
                val report = GsonHolder.instance.fromJson(jsonReport, ReportEntity::class.java)
                binder.report = report
            }
        }
        val profile = UserProfile.getProfile(this)
        binder.profile = profile
        report_details_helper_photo.loadSimple("http://cosmouk.cdnds.net/15/33/768x384/landscape-1439714614-celebrity-face-mashups-taylor-swift-emma-watson.jpg")
        if(profile?.userPicUrl != null)
        report_details_user_photo.loadSimple(profile?.userPicUrl!!)
    }

    override fun onStart() {
        super.onStart()
        val backButton = findViewById(R.id.nav_toolbar_back)
        backButton.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                NavUtils.navigateUpFromSameTask(this@ReportDetailsActivity)
            }
        })
    }
}
