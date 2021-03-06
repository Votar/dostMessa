package com.entregoya.msngr.ui.account.files.presenter

import android.content.Intent
import android.graphics.Bitmap
import com.entregoya.msngr.ui.account.files.model.EntregoFileCategory
import com.entregoya.msngr.ui.account.files.model.UploadPhotoModel
import com.entregoya.msngr.ui.account.files.view.IAddFilesView

/**
 * Created by bertalt on 18.12.16.
 */
interface IAddFilesPresenter {
    fun onCreate(view: IAddFilesView)
    fun onDestroy()
    fun handleResultActivity(requestCode: Int, resultCode: Int, data: Intent?)
    fun pickPhotoFromGallery()
    fun takePhotoFromCamera()
    fun uploadFileToServer(token:String, picture: Bitmap, fileCategory: EntregoFileCategory)
}