package entrego.com.android.ui.account.profile.edit

import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import entrego.com.android.R
import entrego.com.android.storage.model.UserProfileModel
import entrego.com.android.util.Logger
import entrego.com.android.util.UserMessageUtil
import entrego.com.android.util.loading
import entrego.com.android.ui.account.profile.UserProfile
import entrego.com.android.web.model.response.common.FieldErrorResponse
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.navigation_toolbar.*

class EditProfileActivity : AppCompatActivity() {

    companion object {
        val RQT_CODE = 0x691
    }

    object FIELDS {
        val NAME = "name"
        val EMAIL = "email"
        val PHONE_CODE = "phone.code"
        val PHONE_NUMBER = "phone.number"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        navigation_toolbar.title = getString(R.string.titile_edit_user)

        setSupportActionBar(navigation_toolbar)

        nav_toolbar_back.setOnClickListener { onBackPressed() }

        val userProfile = UserProfile.getProfile(this)
        if (userProfile != null && userProfile.phone != null) {
            Logger.logd(userProfile.toString())
            setupView(userProfile)

        } else requestUserProfile()
    }


    fun setupView(profile: UserProfileModel) {
        edit_profile_edit_email.setText(profile.email)
        edit_profile_edit_name.setText(profile.name)
        edit_profile_edit_phone_code.setText(profile.phone.code)
        edit_profile_edit_phone.setText(profile.phone.number)

        edit_profile_btn_save.setOnClickListener { saveData() }
        edit_profile_change_password.setOnClickListener {
            edit_profile_change_password.visibility = View.GONE
            edit_profile_ll_passwords.visibility = View.VISIBLE
            edit_profile_btn_save_pass.visibility = View.VISIBLE
            edit_profile_edit_password.requestFocus()

        }

        edit_profile_btn_save_pass.setOnClickListener {

            edit_profile_il_password.error = null
            edit_profile_il_password_conf.error = null
            val pass = edit_profile_edit_password.text.toString()
            val confPass = edit_profile_edit_password_conf.text.toString()

            if (TextUtils.isEmpty(pass)) {
                edit_profile_il_password.error = getString(R.string.error_empty_fields)
                edit_profile_edit_password.requestFocus()
            } else if (pass != confPass) {
                edit_profile_il_password_conf.error = getString(R.string.error_passwords_not_equals)
                edit_profile_edit_password_conf.requestFocus()

            } else {
                showProgress()

                UserProfile.updatePassword(this, pass,
                        object : UserProfile.ResultUpdatePasswordListener {
                            override fun onFieldError(field: FieldErrorResponse) {
                                edit_profile_il_password.error = field.message
                            }

                            override fun onSuccessUpdate() {

                                hideProgress()
                                UserMessageUtil.show(applicationContext, getString(R.string.ui_success_pass_changed))
                            }

                            override fun onFailureUpdate(message: String) {
                                hideProgress()
                                UserMessageUtil.show(applicationContext, message)
                            }

                        })

            }
        }
    }


    fun saveData() {

        showProgress()
        edit_profile_il_name.error = null
        edit_profile_il_email.error = null
        edit_profile_il_phone.error = null
        edit_profile_il_phone_code.error = null

        UserProfile.update(applicationContext,
                edit_profile_edit_email.text.toString(),
                edit_profile_edit_name.text.toString(),
                edit_profile_edit_phone_code.text.toString(),
                edit_profile_edit_phone.text.toString(),
                object : UserProfile.ResultUpdateListener {
                    override fun onFieldError(field: FieldErrorResponse) {

                        hideProgress()
                        when (field.field) {
                            FIELDS.EMAIL -> {
                                edit_profile_il_email.error = field.message
                            }
                            FIELDS.NAME -> {
                                edit_profile_il_name.error = field.message
                            }
                            FIELDS.PHONE_NUMBER -> {
                                edit_profile_il_phone.error = field.message
                            }
                            FIELDS.PHONE_CODE -> {
                                edit_profile_il_phone_code.error = field.message
                            }
                            else -> showMessage(field.field + " " + field.message)
                        }

                    }

                    override fun onFailureUpdate(message: String) {
                        hideProgress()
                        UserMessageUtil.show(applicationContext, message)
                    }

                    override fun onSuccessUpdate(userProfile: UserProfileModel) {
                        hideProgress()
                        UserMessageUtil.show(applicationContext, getString(R.string.success_profile_updated))
                    }

                }
        )


    }

    fun showProgress() {
        //navigation_progress.visibility = View.VISIBLE
    }

    fun hideProgress() {
        //navigation_progress.visibility = View.GONE
    }

    fun requestUserProfile() {

        showProgress()
        UserProfile.refresh(this, object : UserProfile.ResultRefreshListener {
            override fun onSuccessRefresh(userProfile: UserProfileModel) {
                hideProgress()
                setupView(userProfile)
            }

            override fun onFailureRefresh(message: String) {
                hideProgress()
                showMessage(message)
                finish()
            }

        })
    }


    fun showMessage(message: String) {

        UserMessageUtil.show(this, message)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            android.R.id.home -> NavUtils.navigateUpFromSameTask(this)

        }
        return super.onOptionsItemSelected(item)

    }
}
