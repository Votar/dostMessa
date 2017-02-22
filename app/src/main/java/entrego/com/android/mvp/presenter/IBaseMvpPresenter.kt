package entrego.com.android.mvp.presenter

import entrego.com.android.mvp.view.IBaseMvpView


interface IBaseMvpPresenter<in V : IBaseMvpView> {

    fun attachView(view: V)
    fun detachView()
}