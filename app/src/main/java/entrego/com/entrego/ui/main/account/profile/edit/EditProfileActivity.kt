package entrego.com.entrego.ui.main.account.profile.edit

import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import entrego.com.entrego.R
import entrego.com.entrego.storage.model.UserProfileModel
import entrego.com.entrego.util.Logger
import entrego.com.entrego.util.ToastUtil
import entrego.com.entrego.util.loading
import entrego.com.entrego.ui.main.account.profile.UserProfile
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.navigation_toolbar.*

class EditProfileActivity : AppCompatActivity() {

    companion object {
        val RQT_CODE = 0x691
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        navigation_toolbar.title = getString(R.string.titile_edit_user)

        setSupportActionBar(navigation_toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDefaultDisplayHomeAsUpEnabled(true)


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
        }

        edit_profile_btn_save_pass.setOnClickListener {

            val pass = edit_profile_edit_password.text.toString()
            val confPass = edit_profile_edit_password_conf.text.toString()

            if (pass == confPass) {

                showProgress()
                UserProfile.updatePassword(this, pass,
                        object : UserProfile.ResultUpdatePasswordListener {
                            override fun onSuccessUpdate() {

                                hideProgress()
                                ToastUtil.show(applicationContext, getString(R.string.ui_success_pass_changed))
                            }

                            override fun onFailureUpdate(message: String) {
                                hideProgress()
                                ToastUtil.show(applicationContext, message)
                            }

                        })

            } else {
                ToastUtil.show(this, R.string.error_passwords_not_equals)
            }
        }
    }

    fun saveData() {

        showProgress()
        UserProfile.update(applicationContext,
                edit_profile_edit_email.text.toString(),
                edit_profile_edit_name.text.toString(),
                edit_profile_edit_phone_code.text.toString(),
                edit_profile_edit_phone.text.toString(),
                object : UserProfile.ResultUpdateListener {
                    override fun onFailureUpdate(message: String) {
                        hideProgress()
                        ToastUtil.show(applicationContext, message)
                    }

                    override fun onSuccessUpdate(userProfile: UserProfileModel) {
                        hideProgress()
                        ToastUtil.show(applicationContext, getString(R.string.success_profile_updated))
                    }

                }
        )


    }

    var progress: ProgressDialog? = null
    fun showProgress() {
        navigation_progress.visibility = View.VISIBLE
    }

    fun hideProgress() {
        navigation_progress.visibility = View.GONE
    }

    fun requestUserProfile() {

        showProgress()
        UserProfile.refresh(this, object : UserProfile.ResultRefreshListener {
            override fun onSuccessRefresh(userProfile: UserProfileModel) {
                setupView(userProfile)
                hideProgress()
            }

            override fun onFailureRefresh(message: String) {
                hideProgress()
                showMessage(message)
                finish()
            }

        })
    }


    fun showMessage(message: String) {

        ToastUtil.show(this, message)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            android.R.id.home -> NavUtils.navigateUpFromSameTask(this)

        }
        return super.onOptionsItemSelected(item)

    }
}
