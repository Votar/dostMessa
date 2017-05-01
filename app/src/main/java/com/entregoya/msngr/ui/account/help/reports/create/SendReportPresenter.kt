package com.entregoya.msngr.ui.account.help.reports.create

import com.entregoya.msngr.R
import com.entregoya.msngr.binding.Delivery
import com.entregoya.msngr.mvp.presenter.BaseMvpPresenter
import com.entregoya.msngr.storage.preferences.EntregoStorage
import com.entregoya.msngr.ui.account.help.reports.create.model.SendReportRequest
import com.entregoya.msngr.web.api.ApiContract

class SendReportPresenter : BaseMvpPresenter<SendReportContract.View>(),
        SendReportContract.Presenter {
    val mToken = EntregoStorage.getToken()
    val mListener = object : SendReportRequest.SendReportRequestListener {
        override fun onSuccessSendReportRequest() {
            mView?.hideProgress()

        }

        override fun onFailureSendReportRequest(code: Int?, message: String?) {
            mView?.hideProgress()
            when (code) {
                ApiContract.RESPONSE_OK -> {
                    mView?.showMessage(R.string.success_send_report)
                }
                ApiContract.RESPONSE_INVALID_TOKEN -> mView?.onLogout()
                else -> mView?.showError(message)
            }
        }
    }

    override fun sendReport(text: String) {

        if (text.isEmpty()) {
            mView?.showError(R.string.error_empty_fields)
            return
        }

        val orderId = Delivery.getInstance().id
        if (orderId == 0L)
            mView?.showError(R.string.error_no_order)
        else {
            mView?.showProgress()
            SendReportRequest().executeAsync(mToken, orderId, text, mListener)
        }
    }

}