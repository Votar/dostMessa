package entrego.com.android.ui.auth

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import entrego.com.android.R
import entrego.com.android.util.Logger
import kotlinx.android.synthetic.main.activity_restore_password.*

class RestorePasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restore_password)

        restore_edit_email.setOnClickListener{ Logger.logd("Clicked")}
    }
}
