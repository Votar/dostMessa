package entrego.com.entrego.ui.main.description.view

import android.content.Context
import entrego.com.entrego.storage.model.EntregoPoint

/**
 * Created by bertalt on 07.12.16.
 */
interface IDescreptionView {
    fun getViewContext():Context
    fun showLoadingPoints()
    fun showEmptyView()
    fun showFullDescription(listAddress: List<EntregoPoint>)
}