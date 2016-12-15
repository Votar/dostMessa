package entrego.com.android.ui.main.delivery.description.presenter

import android.os.AsyncTask
import entrego.com.android.storage.model.EntregoPoint
import entrego.com.android.storage.model.EntregoRouteModel
import entrego.com.android.binding.EntregoPointBinding
import entrego.com.android.ui.main.delivery.description.model.GeocoderGetAddress
import entrego.com.android.ui.main.delivery.description.model.GeocoderGetAddressListener
import entrego.com.android.ui.main.delivery.description.view.IDescreptionView

/**
 * Created by bertalt on 07.12.16.
 */
class DescriptionPresenter : IDescriptionPresenter {

    var view: IDescreptionView? = null
    var geocodeWorker: AsyncTask<Void, Void, Boolean>? = null
    val getGeocoderListener = object : GeocoderGetAddressListener {
        override fun onFinishGetAddresses(resultList: List<EntregoPointBinding>) {

        }

    }

    override fun requestAddresses(route: EntregoRouteModel) {

        if (view?.getViewContext() != null) {
            val ctx = view?.getViewContext()!!
            geocodeWorker = GeocoderGetAddress(ctx, getGeocoderListener, route.toList())
                    .execute()
        }
    }

    override fun onStart(view: IDescreptionView) {
        this.view = view
    }

    override fun onStop() {
        geocodeWorker?.cancel(true)
        view = null
    }
}