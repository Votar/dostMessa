package com.entregoya.msngr.ui.main.mvp

import com.entregoya.msngr.mvp.presenter.IBaseMvpPresenter
import com.entregoya.msngr.mvp.view.IBaseMvpView


interface RootContract {
    interface View : IBaseMvpView {

    }

    interface Presenter : IBaseMvpPresenter<View> {
        fun isViewAvailable(): Boolean
        fun viewStarting();
        fun viewDestroying()
    }

}