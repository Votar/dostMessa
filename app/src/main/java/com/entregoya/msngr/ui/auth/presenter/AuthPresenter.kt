package com.entregoya.msngr.ui.auth.presenter

import android.os.Handler
import android.os.Looper
import com.entregoya.msngr.R
import com.entregoya.msngr.storage.preferences.EntregoStorage
import com.entregoya.msngr.ui.account.profile.UserProfile
import com.entregoya.msngr.ui.auth.model.EntregoAuth
import com.entregoya.msngr.ui.auth.presenter.IAuthPresenter
import com.entregoya.msngr.ui.auth.view.IAuthView
import com.entregoya.msngr.ui.account.vehicle.edit.model.UserVehicle


class AuthPresenter(val view: IAuthView) : IAuthPresenter {

    val listener = object : EntregoAuth.ResultListener {
        override fun onSuccessAuth(token: String?) {
            view.hideProgress()
            EntregoStorage.setToken(token)
            UserProfile.refresh(view.getContext(), null)
            UserVehicle.refresh(view.getContext(), null)
            view.goToMainScreen()
        }

        override fun onFailureAuth(message: String?) {
            view.hideProgress()
            if (message != null)
                view.showMessage(message)
        }

    }

    override fun requestAuth(email: String, password: String) {
        if (email.isNullOrEmpty()) {
            view.setErrorEmail(R.string.error_empty_fields)
            return
        }
        if (password.isNullOrEmpty()) {
            view.setErrorPassword(R.string.error_empty_fields)
            return
        }
        val request = EntregoAuth(email, password)
        request.requestAsync(listener)
        view.showProgress()
    }

    override fun requestForgotPassword() {
        view.showProgress()
        Handler(Looper.getMainLooper()).postDelayed(
                object : Runnable {
                    override fun run() {
                        view.hideProgress()
                        view.successRestorePassword()
                    }
                }, 1500)

    }
}