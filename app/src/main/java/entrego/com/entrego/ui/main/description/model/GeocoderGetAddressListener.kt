package entrego.com.entrego.ui.main.description.model

import entrego.com.entrego.storage.model.EntregoPoint

/**
 * Created by bertalt on 07.12.16.
 */
interface GeocoderGetAddressListener {
    fun onFinishGetAddresses(resultList: List<EntregoPoint>)
}