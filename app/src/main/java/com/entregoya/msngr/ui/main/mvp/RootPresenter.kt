package com.entregoya.msngr.ui.main.mvp

import com.entregoya.msngr.mvp.presenter.BaseMvpPresenter

class RootPresenter : BaseMvpPresenter<RootContract.View>(), RootContract.Presenter {
    override fun isViewAvailable(): Boolean = mView != null


}