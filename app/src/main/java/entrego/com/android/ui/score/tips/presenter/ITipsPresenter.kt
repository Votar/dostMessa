package entrego.com.android.ui.score.tips.presenter

import entrego.com.android.ui.score.tips.view.ITipsView

/**
 * Created by bertalt on 29.12.16.
 */
interface ITipsPresenter {
    fun onCreate(view:ITipsView)
    fun onDestroy()
}