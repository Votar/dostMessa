package entrego.com.android.ui.sign.presenter

import entrego.com.android.ui.sign.model.SendSignRequest
import entrego.com.android.ui.sign.view.ISignView

/**
 * Created by bertalt on 13.12.16.
 */
class SignPresenter : ISignPresenter {
    var view: ISignView? = null

    val onSignListener = object : SendSignRequest.SendSignListener {
        override fun onSuccessSendSign() {

            view?.hideProgress()
            view?.onSuccessSign()

        }

        override fun onFailureSendSign(message: String) {
            view?.hideProgress()
            view?.onFailureSign(message)
        }

    }

    override fun onCreate(view: ISignView) {
        this.view = view
    }

    override fun onDestroy() {
        view = null
    }

    override fun sendSign(token: String) {

        view?.showProgress()

        SendSignRequest.executeAsync(token, onSignListener)

    }
}