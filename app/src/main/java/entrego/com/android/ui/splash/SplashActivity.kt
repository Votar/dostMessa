package entrego.com.android.ui.splash

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import entrego.com.android.R
import entrego.com.android.storage.model.UserProfileModel
import entrego.com.android.ui.account.profile.UserProfile
import entrego.com.android.ui.auth.welcome.WelcomeActivity
import entrego.com.android.ui.main.RootActivity

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
            startActivity(Intent(applicationContext, RootActivity::class.java))
            finish()
        }

        override fun onFailureRefresh(message: String?) {
            startActivity(Intent(this@SplashActivity, WelcomeActivity::class.java))
            finish()
        }

    }
}
