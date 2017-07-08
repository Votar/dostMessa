package com.entregoya.msngr.ui.main.mvp

import com.entregoya.msngr.mvp.presenter.BaseMvpPresenter

class RootPresenter : BaseMvpPresenter<RootContract.View>(), RootContract.Presenter {

    private var isViewAlive = false;
    override fun viewDestroying() {
        isViewAlive = false;
    }

    override fun viewStarting() {
        isViewAlive = true;
    }

    override fun isViewAvailable(): Boolean = isViewAlive


}