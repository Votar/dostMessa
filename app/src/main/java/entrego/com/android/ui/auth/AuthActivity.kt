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
import entrego.com.android.R.layout.activity_auth
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
import entrego.com.android.util.showSnackError
import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : AppCompatActivity(), IAuthView {

    companion object {
        const val KEY_LOGOUT = "ext_k_logout"
        fun getIntent(ctx: Context): Intent {
            val intent = Intent(ctx, AuthActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            return intent
        }

        fun getIntentLogout(ctx: Context): Intent =
                getIntent(ctx).apply { putExtra(KEY_LOGOUT, true) }


    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        EntregoStorage.clear()
    }

    override fun onStart() {
        super.onStart()
        setupListeners()
        deserializeIntent()
        val lastEmail = EntregoStorage.getLastEmail()
        if (lastEmail.isNotEmpty()) {
            auth_edit_email.setText(lastEmail)
            auth_edit_password.requestFocus()
        }
    }

    fun deserializeIntent() {
        if (intent.hasExtra(KEY_LOGOUT))
            activity_auth.showSnackError(getString(R.string.error_session_expired))
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
        UserMessageUtil.showSnackMessage(activity_auth, message)
    }

    val presenter: IAuthPresenter = AuthPresenter(this)

    override fun getContext(): Context {
        return applicationContext
    }

    override fun goToMainScreen() {
        val intent = Intent(applicationContext, RootActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        finish()
    }

    override fun setErrorEmail(stringId: Int) {
        auth_edit_email.requestFocus()
        auth_il_email.error = getString(stringId)
    }

    override fun setErrorPassword(stringId: Int) {
        auth_edit_password.requestFocus()
        auth_il_password.error = getString(stringId)
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
                if ((actionId == EditorInfo.IME_ACTION_DONE)
                        || ((event?.keyCode == KeyEvent.KEYCODE_ENTER)
                        && (event.action == KeyEvent.ACTION_DOWN))) {
                    startAuth()
                    return true
                } else
                    return false

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
        auth_il_email.error = ""
        auth_il_password.error = ""
        val email = auth_edit_email.text.toString()
        val password = auth_edit_password.text.toString()
        presenter.requestAuth(email, password)
    }
}
