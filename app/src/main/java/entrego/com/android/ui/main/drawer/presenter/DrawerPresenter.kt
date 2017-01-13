package entrego.com.android.ui.main.drawer.presenter

import android.databinding.Observable
import entrego.com.android.BR
import entrego.com.android.binding.Delivery
import entrego.com.android.ui.main.drawer.view.IDrawerView
import entrego.com.android.ui.main.home.model.DeliveryRequest
import entrego.com.android.util.Logger

/**
 * Created by bertalt on 12.12.16.
 */
class DrawerPresenter : IDrawerPresenter {
    var view: IDrawerView? = null
    val mDeliveryChangedListener = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(p0: Observable?, p1: Int) {

            when (p1) {
                BR.instance -> onBuildView()
            }
        }
    }

    override fun onStart(view: IDrawerView) {

        this.view = view
        Delivery.getInstance().addOnPropertyChangedCallback(mDeliveryChangedListener)
        onBuildView()
    }

    fun onBuildView() {
        val delivery = Delivery.getInstance()
        Logger.logd(delivery.toString())
        view?.refreshView()
    }

    override fun onStop() {
        Delivery.getInstance().removeOnPropertyChangedCallback(mDeliveryChangedListener)
        view = null
    }
}