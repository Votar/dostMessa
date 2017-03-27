package entrego.com.android.ui.main.mvp

import entrego.com.android.mvp.presenter.BaseMvpPresenter

class RootPresenter : BaseMvpPresenter<RootContract.View>(), RootContract.Presenter {
    override fun isViewAvailable(): Boolean = mView != null


}