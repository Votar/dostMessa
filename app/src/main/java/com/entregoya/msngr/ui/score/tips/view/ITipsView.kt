package com.entregoya.msngr.ui.score.tips.view

/**
 * Created by bertalt on 29.12.16.
 */
interface ITipsView {
    fun showMessage(message:String?)
    fun showEmptyView(message:String?)
    fun buildView(list :List<String>)
}