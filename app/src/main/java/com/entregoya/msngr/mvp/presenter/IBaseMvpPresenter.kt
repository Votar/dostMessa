package com.entregoya.msngr.mvp.presenter

import com.entregoya.msngr.mvp.view.IBaseMvpView


interface IBaseMvpPresenter<in V : IBaseMvpView> {

    fun attachView(view: V)
    fun detachView()
}