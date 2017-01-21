package entrego.com.android.ui.incomes.view

import entrego.com.android.entity.IncomeEntity

interface IncomesView {
    fun onBuildCharts(incomes: List<IncomeEntity>)
    fun onShowMessage(message:String?)
    fun onShowMessage(idString:Int)
    fun hideProgress()
}