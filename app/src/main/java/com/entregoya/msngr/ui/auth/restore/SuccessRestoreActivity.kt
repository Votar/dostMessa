package com.entregoya.msngr.ui.auth.restore

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import com.entregoya.msngr.R
import com.entregoya.msngr.ui.auth.AuthActivity
import kotlinx.android.synthetic.main.activity_success_restore.*

class SuccessRestoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success_restore)
    }

    override fun onStart() {
        super.onStart()
        succ_restore_btn_login.setOnClickListener { startActivity(AuthActivity.getIntent(this)) }
    }

}
