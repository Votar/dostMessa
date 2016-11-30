package entrego.com.entrego.ui.registration

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import entrego.com.entrego.R
import entrego.com.entrego.ui.auth.AuthActivity
import entrego.com.entrego.ui.registration.presenter.IRegistrationPresenter
import entrego.com.entrego.ui.registration.presenter.RegistrationPresenter
import entrego.com.entrego.ui.registration.view.IRegistrationView
import entrego.com.entrego.util.ToastUtil
import entrego.com.entrego.util.loading
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity(), IRegistrationView {
    var presenter: IRegistrationPresenter? = null
    override fun setErrorConfPassword() {
        registration_edit_password_conf.requestFocus()
        registration_il_password_conf.error = getString(R.string.error_passwords_not_equals)
    }

    override fun successRegistration() {

        startActivity(Intent(applicationContext, SuccessRegistrationActivity::class.java))
    }

    override fun setErrorEmail(message: String) {

    }

    override fun setErrorPassword(message: String) {

    }

    override fun showMessage(message: String) {

        ToastUtil.show(applicationContext, message)
    }

    var progress: ProgressDialog? = null
    override fun showProgress() {
        progress = ProgressDialog(this)
        progress?.loading()
    }

    override fun hideProgress() {
        progress?.dismiss()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        presenter = RegistrationPresenter(this)

        setupDefaultListeners()
    }


    fun setupDefaultListeners() {

        registration_btn_ok.setOnClickListener {
            presenter?.requestRegistration(
                    registration_edit_email.text.toString(),
                    registration_edit_name.text.toString(),
                    registration_edit_password.text.toString(),
                    registration_edit_password_conf.text.toString(),
                    registration_edit_phone_code.text.toString(),
                    registration_edit_phone.text.toString()
            )

        }
        registration_btn_move_login.setOnClickListener {
            NavUtils.navigateUpFromSameTask(this)
        }
    }

}
