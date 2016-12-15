package entrego.com.android.ui.main.delivery.description.model

import entrego.com.android.storage.model.EntregoPoint
import entrego.com.android.binding.EntregoPointBinding

/**
 * Created by bertalt on 07.12.16.
 */
interface GeocoderGetAddressListener {
    fun onFinishGetAddresses(resultList: List<EntregoPointBinding>)
}