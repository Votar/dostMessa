package entrego.com.android.ui.account.profile.account.presenter

import android.os.Handler
import entrego.com.android.ui.account.profile.account.model.AccountController
import entrego.com.android.ui.account.profile.account.model.AccountModel
import entrego.com.android.ui.account.profile.account.view.IAccountEditView

/**
 * Created by bertalt on 26.12.16.
 */
class AccountEditPresenter : IAccountEditPresenter {
    var mView: IAccountEditView? = null
    val mUpdateListener = object : AccountController.UpdateAccountListener {

        override fun onFieldError(fieldName: String, message: String?) {
            mView?.hideProgress()
            mView?.showFieldError(fieldName, message)
        }

        override fun onAccountFailureUpdate(code: Int, message: String?) {
            mView?.hideProgress()
            mView?.showMessage(message)
        }

        override fun onAccountUpdated(accountModel: AccountModel) {
            mView?.hideProgress()
            mView?.prepareView(accountModel)
        }
    }

    override fun onCreate(view: IAccountEditView) {
        mView = view
        mView?.prepareView(AccountController.getActualData())
    }

    override fun onDestroy() {
        mView = null
    }

    override fun requestChanges(bankName: String, fullName: String, accountNumber: String, swiftCode: String) {
        mView?.showProgress()
        AccountController.requestToUpdate(bankName, fullName, accountNumber, swiftCode, mUpdateListener)
    }

}