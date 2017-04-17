package com.entregoya.msngr.ui.incomes.view

import com.entregoya.msngr.entity.IncomeEntity

interface IncomesView {
    fun onBuildCharts(incomes: List<IncomeEntity>)
    fun onShowMessage(message:String?)
    fun onShowMessage(idString:Int)
    fun hideProgress()
}