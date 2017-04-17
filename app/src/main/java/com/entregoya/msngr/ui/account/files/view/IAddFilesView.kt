package com.entregoya.msngr.ui.account.files.view

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap

interface IAddFilesView {
    fun showMessage(message: String?)
    fun showProgress()
    fun hideProgress()
    fun replaceDocumentHolder(path: String)
    fun replaceDocumentHolder(pic: Bitmap)
    fun successUpload()
    fun getActivityContext(): Activity
}