package entrego.com.android.ui.incomes.history.view

import entrego.com.android.binding.HistoryServiceBinding

/**
 * Created by bertalt on 22.12.16.
 */
interface IHistoryServiceView {
    fun buildTodayServices(list: List<HistoryServiceBinding>)
    fun buildRecentServices(list: List<HistoryServiceBinding>)
    fun showEmptyTodayServicesList()
    fun showEmptyRecentServicesList()

}