package entrego.com.entrego.ui.main.description.model

import entrego.com.entrego.storage.model.EntregoPoint
import entrego.com.entrego.storage.model.binding.EntregoPointBinding

/**
 * Created by bertalt on 07.12.16.
 */
interface GeocoderGetAddressListener {
    fun onFinishGetAddresses(resultList: List<EntregoPointBinding>)
}