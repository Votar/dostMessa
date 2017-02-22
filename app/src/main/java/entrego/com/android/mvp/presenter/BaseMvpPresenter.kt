package entrego.com.android.mvp.presenter

import entrego.com.android.mvp.view.IBaseMvpView


open class BaseMvpPresenter<V : IBaseMvpView> : IBaseMvpPresenter<V> {

    protected var mView: V? = null

    override fun attachView(view: V) {
        mView = view
    }

    override fun detachView() {
        mView = null
    }
}