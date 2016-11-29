package entrego.com.entrego.ui.auth

import android.app.ProgressDialog
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import entrego.com.entrego.R
import entrego.com.entrego.ui.auth.presenter.IAuthPresenter
import entrego.com.entrego.ui.auth.presenter.AuthPresenter
import entrego.com.entrego.ui.auth.view.IAuthView
import entrego.com.entrego.util.ToastUtil
import entrego.com.entrego.util.loading
import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : AppCompatActivity(), IAuthView {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        setupListeners()


    }

    override fun moveToRegistration() {

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
        ToastUtil.show(applicationContext, message)
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


    fun setupListeners() {
        auth_btn_login.setOnClickListener {
            val email = auth_edit_email.text.toString()
            val password = auth_edit_password.text.toString()
            presenter.requestAuth(email, password)
        }

        auth_btn_registration.setOnClickListener { moveToRegistration() }
    }
}
