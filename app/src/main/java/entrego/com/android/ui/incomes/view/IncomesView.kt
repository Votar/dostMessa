package entrego.com.android.ui.incomes.view

import entrego.com.android.entity.IncomeEntity

interface IncomesView {
    fun onBuildCharts(incomes: Array<IncomeEntity>)
    fun onShowMessage(message:String?)
    fun onShowMessage(idString:Int)
}