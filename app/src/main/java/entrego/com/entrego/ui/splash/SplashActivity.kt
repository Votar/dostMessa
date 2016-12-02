package entrego.com.entrego.ui.splash

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import entrego.com.entrego.R
import entrego.com.entrego.storage.model.UserProfileModel
import entrego.com.entrego.ui.auth.AuthActivity
import entrego.com.entrego.ui.main.RootActivity
import entrego.com.entrego.web.model.request.common.UserProfile

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

        override fun onFailureRefresh(message: String) {
            startActivity(Intent(applicationContext, AuthActivity::class.java))
            finish()
        }

    }
}
