package entrego.com.android.ui.registration

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NavUtils
import entrego.com.android.R
import entrego.com.android.ui.auth.AuthActivity
import kotlinx.android.synthetic.main.activity_success_registration.*

class SuccessRegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success_registration)
    }

    override fun onStart() {
        super.onStart()
        succ_reg_btn_login.setOnClickListener {  startActivity(Intent(applicationContext, AuthActivity::class.java))   }
    }

    override fun onBackPressed() {
        NavUtils.navigateUpFromSameTask(this)
    }
}
