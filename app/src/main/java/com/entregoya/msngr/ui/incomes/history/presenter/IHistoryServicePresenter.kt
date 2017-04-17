package com.entregoya.msngr.ui.incomes.history.presenter

import com.entregoya.msngr.ui.incomes.history.view.IHistoryServiceView

/**
 * Created by bertalt on 22.12.16.
 */
interface IHistoryServicePresenter {
    fun onCreate(view: IHistoryServiceView, token: String, range:Pair<Long,Long>)
    fun onDestroy()
    fun refreshHistory(token:String)
    fun requestServiceDetailsById(id: Int)
}