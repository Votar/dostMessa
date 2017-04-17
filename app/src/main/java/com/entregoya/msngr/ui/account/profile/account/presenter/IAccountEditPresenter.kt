package com.entregoya.msngr.ui.account.profile.account.presenter

import com.entregoya.msngr.ui.account.profile.account.view.IAccountEditView

/**
 * Created by bertalt on 26.12.16.
 */
interface IAccountEditPresenter {
    fun onCreate(view :IAccountEditView)
    fun onDestroy()
    fun requestChanges(bankName: String, fullName: String, accountNumber: String, swiftCode: String)
}