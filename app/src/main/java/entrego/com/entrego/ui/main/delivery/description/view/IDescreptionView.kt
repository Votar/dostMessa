package entrego.com.entrego.ui.main.delivery.description.view

import android.content.Context
import entrego.com.entrego.storage.model.EntregoPoint
import entrego.com.entrego.binding.EntregoPointBinding

/**
 * Created by bertalt on 07.12.16.
 */
interface IDescreptionView {
    fun getViewContext(): Context
    fun showEmptyView()
}