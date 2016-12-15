package entrego.com.android.ui.account.help

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.view.MenuItem
import entrego.com.android.R
import entrego.com.android.ui.account.help.chat.ChatHelpActivity
import kotlinx.android.synthetic.main.activity_help.*
import kotlinx.android.synthetic.main.navigation_toolbar.*

class HelpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)

        navigation_toolbar.title = getString(R.string.ui_help)
        setSupportActionBar(navigation_toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDefaultDisplayHomeAsUpEnabled(true)

        help_chatting.setOnClickListener { startChattingActivity() }
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            android.R.id.home -> NavUtils.navigateUpFromSameTask(this)
        }

        return true
    }

    fun startChattingActivity() {

        val intent = Intent(applicationContext, ChatHelpActivity::class.java)
        startActivity(intent)
    }
}
