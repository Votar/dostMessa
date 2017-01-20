package entrego.com.android.ui.incomes.presenter

import entrego.com.android.ui.incomes.view.IncomesView

interface IIncomesPresenter{
    fun requestRange( offset:Int = 0)
    fun onStart(token:String,view: IncomesView)
    fun onStop()
}
