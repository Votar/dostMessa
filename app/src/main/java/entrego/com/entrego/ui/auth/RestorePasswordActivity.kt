package entrego.com.entrego.ui.auth

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import entrego.com.entrego.R
import entrego.com.entrego.util.Logger
import kotlinx.android.synthetic.main.activity_restore_password.*

class RestorePasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restore_password)

        restore_edit_email.setOnClickListener{ Logger.logd("Clicked")}
    }
}
