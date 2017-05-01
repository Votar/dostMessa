package com.entregoya.msngr.ui.account.profile.account.view

import com.entregoya.msngr.ui.account.profile.account.model.AccountEntity


interface IAccountEditView {
    fun showProgress()
    fun hideProgress()
    fun showMessage(message: String?)
    fun showMessage(resStrId: Int)
    fun prepareView(account: AccountEntity)
    fun showFieldError(fieldsName:String, message:String?)
    fun onLogout()
}