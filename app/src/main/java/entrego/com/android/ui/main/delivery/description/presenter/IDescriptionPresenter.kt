package entrego.com.android.ui.main.delivery.description.presenter

import entrego.com.android.ui.main.delivery.description.view.IDescreptionView

interface IDescriptionPresenter {
    fun onStart(view: IDescreptionView)
    fun onStop()

}