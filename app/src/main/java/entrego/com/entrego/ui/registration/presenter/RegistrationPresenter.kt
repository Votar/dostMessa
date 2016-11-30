package entrego.com.entrego.ui.registration.presenter

import entrego.com.entrego.ui.auth.model.EntregoRegistration
import entrego.com.entrego.ui.registration.view.IRegistrationView

/**
 * Created by bertalt on 29.11.16.
 */
class RegistrationPresenter(val view: IRegistrationView) : IRegistrationPresenter {


    override fun requestRegistration(email: String, name: String, password: String, confPassword: String, phoneCode: String, phoneNumber: String) {

        if (password != confPassword) {
            view.setErrorConfPassword()
            return
        }

        view.showProgress()
        EntregoRegistration(email, name, password, phoneCode, phoneNumber)
                .requestAsync(object : EntregoRegistration.ResultListener {
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