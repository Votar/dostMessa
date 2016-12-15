package entrego.com.android.ui.auth.presenter

import android.os.Handler
import android.os.Looper
import entrego.com.android.storage.preferences.EntregoStorage
import entrego.com.android.ui.auth.model.EntregoAuth
import entrego.com.android.ui.auth.presenter.IAuthPresenter
import entrego.com.android.ui.auth.view.IAuthView
import entrego.com.android.ui.account.vehicle.edit.model.UserVehicle

/**
 * Created by bertalt on 29.11.16.
 */
class AuthPresenter(val view: IAuthView) : IAuthPresenter {

    val listener = object : EntregoAuth.ResultListener {
        override fun onSuccessAuth(token: String?) {
            view.hideProgress()

            EntregoStorage(view.getContext()).setToken(token)
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

                }
                , 1500)

    }
}