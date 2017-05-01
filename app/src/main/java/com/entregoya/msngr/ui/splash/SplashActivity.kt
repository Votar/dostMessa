package com.entregoya.msngr.ui.splash

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.entregoya.msngr.R
import com.entregoya.msngr.storage.model.UserProfileModel
import com.entregoya.msngr.storage.preferences.EntregoStorage
import com.entregoya.msngr.ui.account.profile.UserProfile
import com.entregoya.msngr.ui.account.profile.account.model.GetBankAccountRequest
import com.entregoya.msngr.ui.account.vehicle.edit.model.UserVehicle
import com.entregoya.msngr.ui.auth.welcome.WelcomeActivity
import com.entregoya.msngr.ui.main.RootActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slpash)
    }

    override fun onStart() {
        super.onStart()
        UserProfile.refresh(applicationContext, listener)
    }

    val listener = object : UserProfile.ResultRefreshListener {
        override fun onSuccessRefresh(userProfile: UserProfileModel) {
            UserVehicle.refresh(applicationContext, null)
            GetBankAccountRequest().executeAsync(EntregoStorage.getToken(), null)
            startActivity(Intent(applicationContext, RootActivity::class.java))
            finish()
        }

        override fun onFailureRefresh(message: String?) {
            startActivity(Intent(this@SplashActivity, WelcomeActivity::class.java))
            finish()
        }

    }
}
