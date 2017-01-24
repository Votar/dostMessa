package entrego.com.android.ui.main.special

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import entrego.com.android.R
import kotlinx.android.synthetic.main.navigation_toolbar.*

class SpecialDetailsActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, SpecialDetailsActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_special_details)
    }

    override fun onStart() {
        super.onStart()
        nav_toolbar_back.setOnClickListener { NavUtils.navigateUpFromSameTask(this) }
    }
}
