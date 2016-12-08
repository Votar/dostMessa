package entrego.com.entrego.ui.main.description.model

import android.content.Context
import android.location.Geocoder
import android.os.AsyncTask
import entrego.com.entrego.storage.model.EntregoPoint
import entrego.com.entrego.storage.model.EntregoPointStatus

/**
 * Created by bertalt on 07.12.16.
 */
class GeocoderGetAddress(context: Context?, val listener: GeocoderGetAddressListener, val list: List<EntregoPoint>) : AsyncTask<Void, Void, Boolean>() {
    val geocoder: Geocoder

    init {
        geocoder = Geocoder(context)
    }

    override fun doInBackground(vararg p0: Void?): Boolean {

        list[0].status = EntregoPointStatus.NEXT.name
        list[list.size - 1].status = EntregoPointStatus.SCHEDULE.name

        for (next in list) {
            if (isCancelled) break
            val address = geocoder.getFromLocation(next.latitude, next.longitude, 1)
            next.address = address[0].getAddressLine(0)
        }
        return false
    }


    override fun onPostExecute(result: Boolean?) {
        super.onPostExecute(result)
        listener.onFinishGetAddresses(list)
    }
}