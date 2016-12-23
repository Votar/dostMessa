package entrego.com.android.ui.incomes.history.details

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NavUtils
import entrego.com.android.R
import entrego.com.android.ui.incomes.history.details.view.IDetailsOfServiceView
import entrego.com.android.util.UserMessageUtil
import kotlinx.android.synthetic.main.navigation_toolbar.*

class DetailsOfServiceActivity : AppCompatActivity(), IDetailsOfServiceView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_of_service)
        setSupportActionBar(navigation_toolbar)
        nav_toolbar_back.setOnClickListener { NavUtils.navigateUpFromSameTask(this) }
    }

    override fun onShowView() {

    }

    override fun onShowMessage(message: String) {
        UserMessageUtil.show(this, message)
    }

    override fun onShowProgress() {

    }

    override fun onHideProgress() {

    }

}
