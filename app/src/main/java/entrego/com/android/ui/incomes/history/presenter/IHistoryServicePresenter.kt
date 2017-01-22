package entrego.com.android.ui.incomes.history.presenter

import entrego.com.android.ui.incomes.history.view.IHistoryServiceView

/**
 * Created by bertalt on 22.12.16.
 */
interface IHistoryServicePresenter {
    fun onCreate(view: IHistoryServiceView, token: String)
    fun onDestroy()
    fun refreshHistory(token:String)
    fun requestServiceDetailsById(id: Int)
}