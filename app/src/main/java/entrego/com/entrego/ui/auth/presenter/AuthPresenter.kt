package entrego.com.entrego.ui.auth.presenter

import entrego.com.entrego.ui.auth.model.EntregoAuth
import entrego.com.entrego.ui.auth.presenter.IAuthPresenter
import entrego.com.entrego.ui.auth.view.IAuthView

/**
 * Created by bertalt on 29.11.16.
 */
class AuthPresenter(val view: IAuthView) : IAuthPresenter {

    val listener = object : EntregoAuth.ResultListener {
        override fun onSuccessAuth() {
            view.hideProgress()

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

    }
}