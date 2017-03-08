package entrego.com.android.ui.main.delivery.description.presenter

import android.os.AsyncTask
import entrego.com.android.ui.main.delivery.description.view.IDescreptionView

class DescriptionPresenter : IDescriptionPresenter {

    var view: IDescreptionView? = null
    var geocodeWorker: AsyncTask<Void, Void, Boolean>? = null

    override fun onStart(view: IDescreptionView) {
        this.view = view
    }

    override fun onStop() {
        geocodeWorker?.cancel(true)
        view = null
    }
}