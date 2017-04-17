package com.entregoya.msngr.ui.incomes.history.view

import com.entregoya.msngr.binding.HistoryServiceBinding
import com.entregoya.msngr.entity.HistoryServicesPreviewEntity
import com.entregoya.msngr.storage.model.DeliveryModel

/**
 * Created by bertalt on 22.12.16.
 */
interface IHistoryServiceView {
    fun buildTodayServices(list: List<HistoryServicesPreviewEntity>)
    fun buildRecentServices(list: List<HistoryServicesPreviewEntity>)
    fun showEmptyTodayServicesList()
    fun showEmptyRecentServicesList()
    fun showProgress()
    fun hideProgress()
    fun showDetailsHistoryService(model: DeliveryModel)

}