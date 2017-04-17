package com.entregoya.msngr.ui.main.delivery.description.cancel.presenter

import android.support.v7.widget.RecyclerView
import com.entregoya.msngr.ui.main.delivery.description.cancel.view.ICancelDeliveryView

/**
 * Created by bertalt on 20.12.16.
 */
interface ICancelDeliveryPresenter {
    fun onCreate(view: ICancelDeliveryView)
    fun onDestroy()
    fun setupRecyclerView(recycler: RecyclerView, reasons: List<String>)
}