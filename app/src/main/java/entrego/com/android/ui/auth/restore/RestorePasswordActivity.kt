package entrego.com.android.ui.auth.restore

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import entrego.com.android.R
import entrego.com.android.ui.auth.restore.model.RestoreModel
import entrego.com.android.util.Logger
import entrego.com.android.util.loading
import entrego.com.android.util.snackSimple
import entrego.com.android.web.model.response.CommonResponseListener
import kotlinx.android.synthetic.main.activity_restore_password.*

class RestorePasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restore_password)
    }

    override fun onStart() {
        super.onStart()
        respore_password_btn.setOnClickListener {
            if (restore_edit_email.text.isEmpty()) {
                restore_ll_email.error = getString(R.string.error_empty_fields)
            } else {
                mProgress = ProgressDialog(this)
                mProgress?.loading()
                RestoreModel.restorePassword(restore_edit_email.text.toString(), mRestoreListener)
            }
        }
    }

    var mProgress: ProgressDialog? = null
    val mRestoreListener = object : CommonResponseListener {
        override fun onSuccessResponse() {
            mProgress?.hide()
            startActivity(Intent(applicationContext, SuccessRestoreActivity::class.java))
        }

        override fun onFailureResponse(code: Int?, message: String?) {
            mProgress?.hide()
            activity_restore_password.snackSimple(message)
        }

    }
}
