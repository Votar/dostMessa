package entrego.com.android.ui.auth.welcome

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import entrego.com.android.R
import entrego.com.android.ui.auth.AuthActivity
import entrego.com.android.ui.registration.RegistrationActivity
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        welcome_login_btn.setOnClickListener { startActivity(AuthActivity.getIntent(this)) }
        welcome_registr_btn.setOnClickListener { startActivity(Intent(this, RegistrationActivity::class.java)) }
    }
}
