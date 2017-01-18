package entrego.com.android.ui.auth

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import entrego.com.android.R
import entrego.com.android.storage.preferences.EntregoStorage
import entrego.com.android.ui.auth.presenter.AuthPresenter
import entrego.com.android.ui.auth.presenter.IAuthPresenter
import entrego.com.android.ui.auth.restore.RestorePasswordActivity
import entrego.com.android.ui.auth.restore.SuccessRestoreActivity
import entrego.com.android.ui.auth.view.IAuthView
import entrego.com.android.ui.main.RootActivity
import entrego.com.android.ui.registration.RegistrationActivity
import entrego.com.android.util.UserMessageUtil
import entrego.com.android.util.loading
import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : AppCompatActivity(), IAuthView {


    override fun goToMainScreen() {
        val intent = Intent(applicationContext, RootActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        EntregoStorage(this).clear()
        setupListeners()

    }

    override fun goToRegistration() {

        startActivity(Intent(applicationContext, RegistrationActivity::class.java))
    }

    var progress: ProgressDialog? = null
    override fun showProgress() {
        progress = ProgressDialog(this)
        progress?.loading()
    }

    override fun hideProgress() {
        progress?.dismiss()
    }

    override fun showMessage(message: String) {
        UserMessageUtil.showSnackMessage(activity_registration, message)
    }

    val presenter: IAuthPresenter = AuthPresenter(this)

    override fun getContext(): Context {
        return applicationContext
    }

    override fun setErrorEmail(message: String) {
        auth_edit_email.requestFocus()
        auth_il_email.error = getString(R.string.ui_error_email)
    }

    override fun setErrorPassword(message: String) {
        auth_edit_email.requestFocus()
        auth_il_email.error = getString(R.string.ui_error_password)
    }

    override fun successRestorePassword() {
        startActivity(Intent(applicationContext, SuccessRestoreActivity::class.java))
    }

    fun setupListeners() {
        auth_btn_login.setOnClickListener {
            startAuth()
        }

        auth_edit_password.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(p0: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if ((actionId == EditorInfo.IME_ACTION_DONE) || ((event?.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                        && (event?.getAction() == KeyEvent.ACTION_DOWN))) {
                    startAuth()
                    return true
                } else {
                    return false
                }
            }

        })


        auth_btn_registration.setOnClickListener { goToRegistration() }
        auth_btn_forgot_pass.setOnClickListener { startRestoreActivity() }
    }

    private fun startRestoreActivity() {
        val intent = Intent(this, RestorePasswordActivity::class.java)
        startActivity(intent)
    }

    fun startAuth() {
        val email = auth_edit_email.text.toString()
        val password = auth_edit_password.text.toString()
        presenter.requestAuth(email, password)
    }
}
