package entrego.com.entrego.ui.main.delivery.description.presenter

import entrego.com.entrego.storage.model.EntregoPoint
import entrego.com.entrego.storage.model.EntregoRouteModel
import entrego.com.entrego.binding.EntregoPointBinding
import entrego.com.entrego.ui.main.delivery.description.view.IDescreptionView

/**
 * Created by bertalt on 07.12.16.
 */
interface IDescriptionPresenter {
    fun requestAddresses(route: EntregoRouteModel)
    fun onStart(view: IDescreptionView)
    fun onStop()

}