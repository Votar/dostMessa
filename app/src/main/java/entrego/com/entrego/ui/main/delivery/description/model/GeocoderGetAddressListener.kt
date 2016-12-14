package entrego.com.entrego.ui.main.delivery.description.model

import entrego.com.entrego.storage.model.EntregoPoint
import entrego.com.entrego.binding.EntregoPointBinding

/**
 * Created by bertalt on 07.12.16.
 */
interface GeocoderGetAddressListener {
    fun onFinishGetAddresses(resultList: List<EntregoPointBinding>)
}