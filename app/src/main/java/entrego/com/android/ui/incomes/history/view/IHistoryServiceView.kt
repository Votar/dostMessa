package entrego.com.android.ui.incomes.history.view

import entrego.com.android.binding.HistoryServiceBinding
import entrego.com.android.entity.HistoryServicesPreviewEntity
import entrego.com.android.storage.model.DeliveryModel

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