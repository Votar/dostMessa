package com.entregoya.msngr.ui.auth.presenter

/**
 * Created by bertalt on 29.11.16.
 */
interface IAuthPresenter {
    fun requestAuth(email: String, password: String)
    fun requestForgotPassword()
}