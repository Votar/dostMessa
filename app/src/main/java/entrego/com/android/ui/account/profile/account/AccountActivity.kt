package entrego.com.android.ui.account.profile.account

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v4.app.NavUtils
import android.widget.EditText
import entrego.com.android.R
import entrego.com.android.ui.account.profile.account.AccountActivity.AccountFields.*
import entrego.com.android.ui.account.profile.account.model.AccountEntity
import entrego.com.android.ui.account.profile.account.presenter.AccountEditPresenter
import entrego.com.android.ui.account.profile.account.presenter.IAccountEditPresenter
import entrego.com.android.ui.account.profile.account.view.IAccountEditView
import entrego.com.android.util.loading
import kotlinx.android.synthetic.main.activity_account.*
import kotlinx.android.synthetic.main.navigation_toolbar.*

class AccountActivity : AppCompatActivity(), IAccountEditView {

    var mActivityFields: List<EditText> = emptyList()
    var mActivityInputLayouts: List<TextInputLayout> = emptyList()
    var progressDialog: ProgressDialog? = null
    var mPresenter: IAccountEditPresenter = AccountEditPresenter()

    enum class AccountFields(name: String) {
        BANK_NAME("bank_name"),
        FULL_NAME("full_name"),
        ACCOUNT_NUMBER("account_number"),
        SWIFT_CODE("swift_code")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)
        mPresenter.onCreate(this)
    }

    override fun onStart() {
        super.onStart()
        account_btn_save.setOnClickListener { requestUpdate() }
        setSupportActionBar(navigation_toolbar)
        nav_toolbar_back.setOnClickListener { NavUtils.navigateUpFromSameTask(this) }
        mActivityFields = listOf(account_edit_bank_name, account_edit_full_name, account_edit_number, account_edit_swift_code)
        mActivityInputLayouts = listOf(account_bank_name_il, account_full_name_il, account_number_il, account_swift_il)
    }


    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroy()
    }

    override fun showProgress() {
        progressDialog = ProgressDialog(this)
        progressDialog?.dismiss()
        progressDialog?.loading()
    }

    override fun hideProgress() {
        progressDialog?.dismiss()
    }

    override fun showMessage(message: String?) {

    }

    override fun prepareView(account: AccountEntity) {
        account_edit_bank_name.setText(account.bankName)
        account_edit_full_name.setText(account.fullName)
        account_edit_number.setText(account.accountNumber)
        account_edit_swift_code.setText(account.swiftCode)
        mActivityFields.forEach { it.error = null }
        mActivityInputLayouts.forEach { it.isErrorEnabled = false }
    }

    override fun showFieldError(fieldsName: String, message: String?) {

        var selectedView: TextInputLayout? = null
        when (AccountFields.valueOf(fieldsName)) {
            BANK_NAME -> {
                selectedView = account_bank_name_il
            }
            FULL_NAME -> {
                selectedView = account_full_name_il
            }
            ACCOUNT_NUMBER -> {
                selectedView = account_number_il
            }
            SWIFT_CODE -> {
                selectedView = account_swift_il
            }
        }
        selectedView?.isErrorEnabled = true
        selectedView?.error = message
    }

    private fun requestUpdate() {

        val bankName = account_edit_bank_name.text.toString()
        val fullName = account_edit_full_name.text.toString()
        val accountNumber = account_edit_number.text.toString()
        val swiftCode = account_edit_swift_code.text.toString()

        mPresenter.requestChanges(bankName, fullName, accountNumber, swiftCode)
    }

}
