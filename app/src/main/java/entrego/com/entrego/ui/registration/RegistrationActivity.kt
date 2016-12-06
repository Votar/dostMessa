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
import entrego.com.entrego.util.UserMessageUtil
import entrego.com.entrego.util.loading
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity(), IRegistrationView {

    override fun setErrorName(message: String) {
        registration_edit_name.requestFocus()
        registration_il_name.error = message
    }

    override fun setErrorPhoneCode(message: String) {
        registration_edit_phone_code.requestFocus()
        registration_il_phone_code.error = message
    }

    override fun setErrorPhoneNumber(message: String) {

        registration_edit_phone.requestFocus()
        registration_il_phone.error = message
    }

    var presenter: IRegistrationPresenter? = null
    override fun setErrorConfPassword() {
        registration_edit_password_conf.requestFocus()
        registration_il_password_conf.error = getString(R.string.error_passwords_not_equals)
    }

    override fun successRegistration() {

        startActivity(Intent(applicationContext, SuccessRegistrationActivity::class.java))
    }

    override fun setErrorEmail(message: String) {
        registration_edit_email.requestFocus()
        registration_il_email.error = message
    }

    override fun setErrorPassword(message: String) {
        registration_edit_password.requestFocus()
        registration_il_password.error = message
    }

    override fun showMessage(message: String) {

        UserMessageUtil.show(applicationContext, message)
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

            registration_il_name.error = null
            registration_il_email.error = null
            registration_il_password.error = null
            registration_il_password_conf.error = null
            registration_il_phone_code.error = null
            registration_il_phone.error = null

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
