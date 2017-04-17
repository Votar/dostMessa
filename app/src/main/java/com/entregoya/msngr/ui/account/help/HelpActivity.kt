package com.entregoya.msngr.ui.account.help

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.entregoya.msngr.R
import com.entregoya.msngr.ui.account.help.reports.ReportsListActivity
import kotlinx.android.synthetic.main.activity_help.*
import kotlinx.android.synthetic.main.navigation_toolbar.*

class HelpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)

        navigation_toolbar.title = getString(R.string.ui_help)
        setSupportActionBar(navigation_toolbar)

        nav_toolbar_back.setOnClickListener { onBackPressed() }
        help_chatting.setOnClickListener { startChattingActivity() }
        help_incidents_ll.setOnClickListener { startReportsActivity() }
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            android.R.id.home -> NavUtils.navigateUpFromSameTask(this)
        }

        return true
    }

    private fun startReportsActivity() {
        startActivity(Intent(this, ReportsListActivity::class.java))
    }

    fun startChattingActivity() {

//        val intent = Intent(applicationContext, ChatHelpActivity::class.java)
//        startActivity(intent)
    }
}
