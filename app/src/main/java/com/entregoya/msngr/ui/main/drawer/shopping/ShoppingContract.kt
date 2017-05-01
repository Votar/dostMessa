package com.entregoya.msngr.ui.main.drawer.shopping

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import com.entregoya.msngr.mvp.presenter.IBaseMvpPresenter
import com.entregoya.msngr.mvp.view.IBaseMvpView


interface ShoppingContract {
    interface View : IBaseMvpView {
        fun deserializeIntent()
        fun getActivityContext(): Activity
        fun setupPhotoHolder(picture: Bitmap)
        fun setupDefaultHolder()
        fun showProgress()
        fun hideProgress()
    }

    interface Presenter : IBaseMvpPresenter<View> {
        fun requestPhoto()
        fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
        fun sendReceipt(amount: String)
        fun setupOrderId(orderId: Long)
    }
}