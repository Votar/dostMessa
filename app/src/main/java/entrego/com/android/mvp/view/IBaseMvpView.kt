package entrego.com.android.mvp.view

import android.content.Context
import android.support.annotation.StringRes
import android.view.View


interface IBaseMvpView {

    fun getRootView(): View?

    fun getAppContext(): Context

    fun showError(error: String?)

    fun showError(@StringRes stringResId: Int)

    fun showMessage(@StringRes srtResId: Int)

    fun showMessage(message: String)

    fun onLogout()


}

