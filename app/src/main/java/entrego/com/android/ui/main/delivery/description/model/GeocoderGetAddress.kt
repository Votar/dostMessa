package entrego.com.android.ui.main.delivery.description.model

import android.content.Context
import android.location.Geocoder
import android.os.AsyncTask
import entrego.com.android.storage.model.EntregoPointStatus
import entrego.com.android.binding.EntregoPointBinding
import java.io.IOException

/**
 * Created by bertalt on 07.12.16.
 */
class GeocoderGetAddress(context: Context?, val listener: GeocoderGetAddressListener, val list: List<EntregoPointBinding>) : AsyncTask<Void, Void, Boolean>() {
    val geocoder: Geocoder

    init {
        geocoder = Geocoder(context)
    }

    override fun doInBackground(vararg p0: Void?): Boolean {

        list[0].status = EntregoPointStatus.NEXT.name
        list[list.size - 1].status = EntregoPointStatus.SCHEDULE.name


        for (next in list) {
            if (isCancelled) break
            try {
                val address = geocoder.getFromLocation(next.latitude, next.longitude, 1)
                next.address = address[0].getAddressLine(0)
            } catch (ex: IOException) {
                next.address ="Error"
            }
        }

        return false
    }


    override fun onPostExecute(result: Boolean?) {
        super.onPostExecute(result)
        listener.onFinishGetAddresses(list)
    }
}