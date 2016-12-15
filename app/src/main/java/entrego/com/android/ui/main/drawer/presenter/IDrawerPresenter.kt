package entrego.com.android.ui.main.drawer.presenter

import entrego.com.android.ui.main.drawer.view.IDrawerView

/**
 * Created by bertalt on 12.12.16.
 */
interface IDrawerPresenter {


    fun onStart(view: IDrawerView)
    fun onStop()
}