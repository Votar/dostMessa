package entrego.com.entrego.ui.main.description.presenter

import android.os.AsyncTask
import entrego.com.entrego.storage.model.EntregoPoint
import entrego.com.entrego.ui.main.description.model.GeocoderGetAddress
import entrego.com.entrego.ui.main.description.model.GeocoderGetAddressListener
import entrego.com.entrego.ui.main.description.view.IDescreptionView

/**
 * Created by bertalt on 07.12.16.
 */
class DescriptionPresenter : IDescriptionPresenter {

    var view : IDescreptionView? = null
    var geocodeWorker :AsyncTask<Void, Void, Boolean>? = null
    val getGeocoderListener = object : GeocoderGetAddressListener {
        override fun onFinishGetAddresses(resultList: List<EntregoPoint>) {
            view?.showFullDescription(resultList)
        }

    }

    override fun requestAddresses(list: List<EntregoPoint>) {
        view?.showLoadingPoints()

        if (view?.getViewContext() != null) {
            val ctx = view?.getViewContext()!!
            geocodeWorker = GeocoderGetAddress(ctx, getGeocoderListener, list)
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