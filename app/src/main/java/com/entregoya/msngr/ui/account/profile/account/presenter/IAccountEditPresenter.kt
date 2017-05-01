package com.entregoya.msngr.ui.account.profile.account.presenter

import com.entregoya.msngr.ui.account.profile.account.view.IAccountEditView

interface IAccountEditPresenter {
    fun onCreate(view :IAccountEditView)
    fun onDestroy()
    fun requestChanges(bankName: String, fullName: String, accountNumber: String, swiftCode: String)
}