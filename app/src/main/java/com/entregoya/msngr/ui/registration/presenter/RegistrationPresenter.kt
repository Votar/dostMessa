package com.entregoya.msngr.ui.registration.presenter

import com.entregoya.msngr.R
import com.entregoya.msngr.ui.registration.model.EntregoRegistration
import com.entregoya.msngr.ui.registration.view.IRegistrationView
import com.entregoya.msngr.web.model.response.common.FieldErrorResponse

/**
 * Created by bertalt on 29.11.16.
 */
class RegistrationPresenter(val view: IRegistrationView) : IRegistrationPresenter {

    enum class FIELDS(val value: String) {
        EMAIL("email"),
        NAME("name"),
        PASSWORD("password"),
        PHONE_NUMBER("phone.number"),
        PHONE_CODE("phone.code")
    }

    override fun requestRegistration(email: String, name: String, password: String, confPassword: String, phoneCode: String, phoneNumber: String) {

        if (password != confPassword) {
            view.setErrorConfPassword()
            return
        }

        view.showProgress()
        EntregoRegistration(email, name, password, phoneCode, phoneNumber)
                .requestAsync(object : EntregoRegistration.ResultListener {
                    override fun onValidationError(field: FieldErrorResponse) {

                        view.hideProgress()

                        when (field.field) {

                            FIELDS.EMAIL.value,
                            FIELDS.PHONE_NUMBER.value-> {
                                view.setErrorEmail(view.getContext().getString(R.string.registered_field))
                                view.setErrorPhoneNumber(view.getContext().getString(R.string.registered_field))
                            }

                            FIELDS.NAME.value -> view.setErrorName(field.message)

                            FIELDS.PASSWORD.value -> view.setErrorPassword(field.message)

                            else -> view.showMessage(field.message)
                        }


                    }

                    override fun onSuccessRegistration() {
                        view.hideProgress()

                        view.successRegistration()
                    }

                    override fun onFailureRegistration(message: String?, code: Int?) {
                        view.hideProgress()

                        when (code) {
                            3 -> {
                                view.setErrorEmailRegistered()
                                view.setErrorPhoneRegistered()
                            }
                            else -> view.showMessage(message)
                        }
                    }

                })


    }


}