package com.entregoya.msngr.ui.account.help.reports.presenter

import com.entregoya.msngr.ui.account.help.reports.view.IReportsListView

/**
 * Created by bertalt on 26.12.16.
 */
interface IReportsListPresenter {
    fun onCreate(view: IReportsListView)
    fun onDestroy()
}