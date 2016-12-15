package entrego.com.android.ui.main.delivery.description.presenter

import entrego.com.android.storage.model.EntregoPoint
import entrego.com.android.storage.model.EntregoRouteModel
import entrego.com.android.binding.EntregoPointBinding
import entrego.com.android.ui.main.delivery.description.view.IDescreptionView

/**
 * Created by bertalt on 07.12.16.
 */
interface IDescriptionPresenter {
    fun requestAddresses(route: EntregoRouteModel)
    fun onStart(view: IDescreptionView)
    fun onStop()

}