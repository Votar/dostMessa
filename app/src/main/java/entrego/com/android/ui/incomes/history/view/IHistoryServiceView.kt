package entrego.com.android.ui.incomes.history.view

import entrego.com.android.binding.HistoryServiceBinding
import entrego.com.android.entity.HistoryServicesPreviewEntity

/**
 * Created by bertalt on 22.12.16.
 */
interface IHistoryServiceView {
    fun buildTodayServices(list: List<HistoryServicesPreviewEntity>)
    fun buildRecentServices(list: List<HistoryServicesPreviewEntity>)
    fun showEmptyTodayServicesList()
    fun showEmptyRecentServicesList()

}