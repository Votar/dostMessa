package com.entregoya.msngr.ui.account.profile.account.presenter

import com.entregoya.msngr.R
import com.entregoya.msngr.storage.preferences.EntregoStorage
import com.entregoya.msngr.ui.account.profile.account.model.AccountEntity
import com.entregoya.msngr.ui.account.profile.account.model.BankAccountChangeRequest
import com.entregoya.msngr.ui.account.profile.account.view.IAccountEditView
import com.entregoya.msngr.web.api.ApiContract


class AccountEditPresenter : IAccountEditPresenter {
    var mView: IAccountEditView? = null
    val mToken = EntregoStorage.getToken()
    val mResponseListener = object : BankAccountChangeRequest.BankAccountChangeRequestListener {
        override fun onSuccessBankAccountChangeRequest(updatedAccount: AccountEntity) {
            mView?.hideProgress()
            mView?.prepareView(updatedAccount)
            EntregoStorage.saveBankAccount(updatedAccount)
        }

        override fun onFailureBankAccountChangeRequest(code: Int?, message: String?) {
            mView?.hideProgress()
            when(code){
                ApiContract.RESPONSE_INVALID_TOKEN->mView?.onLogout()
                ApiContract.VALIDATION_ERROR->mView?.showMessage(R.string.error_validation_field)
                else->mView?.showMessage(message)
            }
        }

    }

    override fun onCreate(view: IAccountEditView) {
        mView = view
        EntregoStorage.getBankAccount()?.also { mView?.prepareView(it) }
    }

    override fun onDestroy() {
        mView = null
    }

    override fun requestChanges(bankName: String, fullName: String, accountNumber: String, swiftCode: String) {
        mView?.showProgress()
        BankAccountChangeRequest().executeAsync(mToken,
                swiftCode,
                accountNumber,
                bankName,
                fullName,
                mResponseListener)
    }

}