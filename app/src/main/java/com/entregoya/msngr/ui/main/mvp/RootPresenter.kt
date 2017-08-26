package com.entregoya.msngr.ui.main.mvp

import android.util.Log
import com.entregoya.msngr.R
import com.entregoya.msngr.mvp.presenter.BaseMvpPresenter
import com.entregoya.msngr.storage.preferences.EntregoStorage
import com.entregoya.msngr.web.fcm.models.SendFcmTokenRequest
import com.google.firebase.iid.FirebaseInstanceId

class RootPresenter : BaseMvpPresenter<RootContract.View>(), RootContract.Presenter {
    companion object {
        const val TAG = "RootPresenter"
    }

    private var isViewAlive = false;
    override fun viewDestroying() {
        isViewAlive = false;
    }

    override fun viewStarting() {
        isViewAlive = true;
        val fcmToken = FirebaseInstanceId.getInstance().token
        if (fcmToken?.isNotEmpty() == true)
            sendRegistrationToServer(fcmToken)
        else
            mView?.showMessage(R.string.error_fcm_token_is_null)
    }

    override fun isViewAvailable(): Boolean = isViewAlive


    private fun sendRegistrationToServer(refreshedToken: String) {
        val token = EntregoStorage.getToken()
        SendFcmTokenRequest().executeAsync(token, refreshedToken, sendTokenListener)
    }

    val sendTokenListener = object : SendFcmTokenRequest.ResponseListener {
        override fun onSuccess() {
            Log.d(TAG, "Success")
        }

        override fun onFailure(code: Int?, message: String?) {
            Log.d(TAG, "onFailure")
        }
    }
}