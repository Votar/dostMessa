package com.entregoya.msngr.ui.account.files.model

interface UploadPhotoListener {
    fun successUploadFile()
    fun failureUploadFile(code: Int?, message: Int?)
}