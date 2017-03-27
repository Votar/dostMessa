package entrego.com.android.ui.main.mvp

import entrego.com.android.mvp.presenter.IBaseMvpPresenter
import entrego.com.android.mvp.view.IBaseMvpView


interface RootContract {
    interface View : IBaseMvpView {

    }

    interface Presenter : IBaseMvpPresenter<View> {
        fun isViewAvailable(): Boolean
    }

}