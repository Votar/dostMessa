package com.entregoya.msngr.mvp.view

import android.content.Context
import android.support.v4.app.Fragment
import android.view.View
import com.entregoya.msngr.mvp.presenter.IBaseMvpPresenter
import com.entregoya.msngr.util.logout
import com.entregoya.msngr.util.showSnack
import com.entregoya.msngr.util.showSnackError

abstract class BaseMvpFragment<in V : IBaseMvpView, T : IBaseMvpPresenter<V>> : Fragment(),
        IBaseMvpView {
    protected abstract var mPresenter: T


    override fun onStart() {
        super.onStart()
        mPresenter.attachView(this as V)
    }

    override fun onStop() {
        super.onStop()
        mPresenter.detachView()
    }

    override fun getRootView(): View? = view

    override fun getAppContext(): Context = activity

    override fun showError(error: String?) {
        getRootView()?.showSnackError(error)
    }

    override fun showError(stringResId: Int) {
        getRootView()?.showSnackError(getString(stringResId))
    }

    override fun showMessage(srtResId: Int) {
        getRootView()?.showSnack(getString(srtResId))
    }

    override fun showMessage(message: String) {
        getRootView()?.showSnack(message)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    override fun onLogout() {
        activity?.logout()
    }

}