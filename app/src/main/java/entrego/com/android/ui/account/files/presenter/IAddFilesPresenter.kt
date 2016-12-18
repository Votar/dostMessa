package entrego.com.android.ui.account.files.presenter

import android.content.Intent
import entrego.com.android.ui.account.files.view.IAddFilesView

/**
 * Created by bertalt on 18.12.16.
 */
interface IAddFilesPresenter {
    fun onCreate(view: IAddFilesView)
    fun onDestroy()
    fun handleResultActivity(requestCode: Int, resultCode: Int, data: Intent?)
    fun pickPhotoFromGallery()
    fun takePhotoFromCamera()
}