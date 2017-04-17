package com.entregoya.msngr.ui.registration.view

import android.content.Context

interface IRegistrationView {
    fun setErrorName(message:String)
    fun setErrorEmail(message: String)
    fun setErrorPassword(message: String)
    fun setErrorPhoneCode(message:String)
    fun setErrorPhoneNumber(message:String)
    fun setErrorConfPassword()
    fun setErrorEmailRegistered()
    fun showMessage(message:String?)
    fun showProgress()
    fun hideProgress()
    fun successRegistration()
    fun getContext(): Context
    fun setErrorPhoneRegistered()
}