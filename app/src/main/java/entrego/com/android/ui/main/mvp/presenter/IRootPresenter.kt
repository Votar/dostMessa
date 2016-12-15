package entrego.com.android.ui.main.mvp.presenter

import entrego.com.android.ui.main.mvp.view.IRootView

/**
 * Created by bertalt on 12.12.16.
 */
interface IRootPresenter {
    fun onCreate(view: IRootView)
    fun onDestroy()

}