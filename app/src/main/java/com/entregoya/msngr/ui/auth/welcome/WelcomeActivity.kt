package com.entregoya.msngr.ui.auth.welcome

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.entregoya.msngr.R
import com.entregoya.msngr.ui.auth.AuthActivity
import com.entregoya.msngr.ui.registration.RegistrationActivity
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        welcome_login_btn.setOnClickListener { startActivity(AuthActivity.getIntent(this)) }
        welcome_registr_btn.setOnClickListener { startActivity(Intent(this, RegistrationActivity::class.java)) }
    }
}
