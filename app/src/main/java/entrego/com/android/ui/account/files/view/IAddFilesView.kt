package entrego.com.android.ui.account.files.view

import android.app.Activity
import android.content.Context

/**
 * Created by bertalt on 18.12.16.
 */
interface IAddFilesView {
    fun showMessage(message: String?)
    fun showProgress()
    fun hideProgress()
    fun getActivityContext(): Activity
    fun replaceDocumentHolder(path: String)
}