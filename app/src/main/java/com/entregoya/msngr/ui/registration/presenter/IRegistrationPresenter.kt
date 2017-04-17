package com.entregoya.msngr.ui.registration.presenter

import com.entregoya.msngr.ui.registration.view.IRegistrationView

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