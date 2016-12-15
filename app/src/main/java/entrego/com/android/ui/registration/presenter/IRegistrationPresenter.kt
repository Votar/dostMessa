package entrego.com.android.ui.registration.presenter

import entrego.com.android.ui.registration.view.IRegistrationView

/**
 * Created by bertalt on 29.11.16.
 */
interface IRegistrationPresenter {
    fun requestRegistration(email: String,
                            name: String,
                            password: String, confPassword: String,
                            phoneCode: String,
                            phoneNumber: String)
}