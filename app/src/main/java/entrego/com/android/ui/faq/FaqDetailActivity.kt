package entrego.com.android.ui.faq

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.view.View
import entrego.com.android.R
import kotlinx.android.synthetic.main.activity_faq_detail.*
import kotlinx.android.synthetic.main.navigation_toolbar.*

class FaqDetailActivity : AppCompatActivity() {

    companion object {
        val EXT_TITLE = "ext_title_faq"
        val EXT_MSG = "ext_msg_faq"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faq_detail)

        nav_toolbar_back.setOnClickListener { NavUtils.navigateUpFromSameTask(this) }
        nav_toolbar_faq.visibility = View.GONE
        if (intent != null) {
            val title = intent.getStringExtra(EXT_TITLE)
            val message = intent.getStringExtra(EXT_MSG)
            faq_det_title.text = title
            faq_det_body.text = message
        }
    }
}
