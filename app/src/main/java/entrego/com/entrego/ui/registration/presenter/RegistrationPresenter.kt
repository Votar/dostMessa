package entrego.com.entrego.ui.registration.presenter

import entrego.com.entrego.ui.auth.model.EntregoRegistration
import entrego.com.entrego.ui.registration.view.IRegistrationView
import entrego.com.entrego.web.model.response.common.FieldErrorResponse

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

                            FIELDS.EMAIL.value -> view.setErrorEmail(field.message)

                            FIELDS.NAME.value -> view.setErrorName(field.message)

                            FIELDS.PASSWORD.value -> view.setErrorPassword(field.message)

                            FIELDS.PHONE_CODE.value -> view.setErrorPhoneCode(field.message)

                            FIELDS.PHONE_NUMBER.value -> view.setErrorPhoneNumber(field.message)

                            else -> view.showMessage(field.message)
                        }


                    }

                    override fun onSuccessRegistration() {
                        view.hideProgress()

                        view.successRegistration()
                    }

                    override fun onFailureRegistration(message: String?) {
                        view.hideProgress()

                        view.showMessage(message!!)
                    }

                })


    }


}