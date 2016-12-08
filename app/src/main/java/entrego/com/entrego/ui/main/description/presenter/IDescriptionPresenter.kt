package entrego.com.entrego.ui.main.description.presenter

import entrego.com.entrego.storage.model.EntregoPoint
import entrego.com.entrego.ui.main.description.view.IDescreptionView

/**
 * Created by bertalt on 07.12.16.
 */
interface IDescriptionPresenter {
    fun requestAddresses(list: List<EntregoPoint>)
    fun onStart(view: IDescreptionView)
    fun onStop()

}