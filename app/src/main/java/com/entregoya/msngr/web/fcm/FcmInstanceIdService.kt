package com.entregoya.msngr.web.fcm

import android.util.Log
import com.entregoya.msngr.storage.preferences.EntregoStorage
import com.entregoya.msngr.web.fcm.models.SendFcmTokenRequest
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService


class FcmInstanceIdService : FirebaseInstanceIdService() {
    companion object {
        const val TAG = "FcmInstanceIdService"
    }

    override fun onTokenRefresh() {
        // Get updated InstanceID token.
        val refreshedToken = FirebaseInstanceId.getInstance().token
        Log.d(TAG, "Refreshed token: " + refreshedToken!!)

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken)
    }

    private fun sendRegistrationToServer(refreshedToken: String) {
        val token = EntregoStorage.getToken()
        SendFcmTokenRequest().executeAsync(token, refreshedToken, sendTokenListener)
    }

    val sendTokenListener = object: SendFcmTokenRequest.ResponseListener{
        override fun onSuccess() {
            Log.d(TAG, "Success")
        }

        override fun onFailure(code: Int?, message: String?) {
            Log.d(TAG, "onFailure")
        }

    }
}