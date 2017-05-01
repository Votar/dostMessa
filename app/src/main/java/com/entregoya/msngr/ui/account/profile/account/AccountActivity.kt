package com.entregoya.msngr.ui.account.profile.account

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v4.app.NavUtils
import android.widget.EditText
import com.entregoya.msngr.R
import com.entregoya.msngr.ui.account.profile.account.AccountActivity.AccountFields.*
import com.entregoya.msngr.ui.account.profile.account.model.AccountEntity
import com.entregoya.msngr.ui.account.profile.account.presenter.AccountEditPresenter
import com.entregoya.msngr.ui.account.profile.account.presenter.IAccountEditPresenter
import com.entregoya.msngr.ui.account.profile.account.view.IAccountEditView
import com.entregoya.msngr.util.loading
import com.entregoya.msngr.util.logout
import com.entregoya.msngr.util.snackSimple
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
        activity_account.snackSimple(message)
    }

    override fun prepareView(account: AccountEntity) {
        account_edit_bank_name.setText(account.bank)
        account_edit_full_name.setText(account.name)
        account_edit_number.setText(account.account)
        account_edit_swift_code.setText(account.swift)
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

    override fun showMessage(resStrId: Int) {
        activity_account.snackSimple(getString(resStrId))
    }

    override fun onLogout() {
        this.logout()
    }


}
