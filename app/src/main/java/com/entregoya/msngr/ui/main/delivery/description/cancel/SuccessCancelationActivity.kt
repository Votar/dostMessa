package com.entregoya.msngr.ui.main.delivery.description.cancel

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.view.View
import com.entregoya.msngr.R
import kotlinx.android.synthetic.main.navigation_toolbar.*

class SuccessCancelationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success_cancelation)
        setSupportActionBar(navigation_toolbar)
        nav_toolbar_back.setOnClickListener({ NavUtils.navigateUpFromSameTask(this) })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        NavUtils.navigateUpFromSameTask(this)
    }
}
