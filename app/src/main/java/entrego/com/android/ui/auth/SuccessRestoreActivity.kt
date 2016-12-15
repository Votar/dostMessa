package entrego.com.android.ui.auth

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import entrego.com.android.R
import kotlinx.android.synthetic.main.activity_success_restore.*

class SuccessRestoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success_restore)

        succ_restore_btn_login.setOnClickListener {
            startActivity(Intent(applicationContext, AuthActivity::class.java))
        }
    }

    override fun onBackPressed() {
        NavUtils.navigateUpFromSameTask(this)
    }
}