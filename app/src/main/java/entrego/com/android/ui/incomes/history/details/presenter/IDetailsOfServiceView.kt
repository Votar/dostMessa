package entrego.com.android.ui.incomes.history.details.presenter

import entrego.com.android.ui.incomes.history.details.view.IDetailsOfServiceView

/**
 * Created by bertalt on 23.12.16.
 */
interface IDetailsOfServicePresenter {
    fun onCreate(view : IDetailsOfServiceView)
    fun onDestroy()
}