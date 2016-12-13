package entrego.com.entrego.ui.main.drawer.presenter

import entrego.com.entrego.ui.main.drawer.view.IDrawerView

/**
 * Created by bertalt on 12.12.16.
 */
interface IDrawerPresenter {


    fun onStart(view: IDrawerView)
    fun onStop()
}