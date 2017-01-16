package entrego.com.android.ui.main.drawer.sign.presenter

import android.graphics.Bitmap
import entrego.com.android.binding.Delivery
import entrego.com.android.ui.main.drawer.sign.model.SendSignRequest
import entrego.com.android.ui.main.drawer.sign.view.ISignView

class SignPresenter : ISignPresenter {
    var view: ISignView? = null

    val mListener = object : SendSignRequest.SendSignListener {
        override fun onSuccessSendSign() {
            view?.hideProgress()
            view?.onSuccessSign()
        }

        override fun onFailureSendSign(code: Int?, message: String?) {
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

    override fun sendSign(token: String, signBill: Bitmap) {
        view?.showProgress()
        Delivery.getInstance().id
        SendSignRequest.executeAsync(token,
                Delivery.getInstance().id,
                signBill,
                mListener)
    }
}