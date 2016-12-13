package entrego.com.entrego.ui.main.drawer.presenter

import android.databinding.Observable
import entrego.com.entrego.BR
import entrego.com.entrego.storage.model.binding.DeliveryInstance
import entrego.com.entrego.ui.main.drawer.view.IDrawerView
import entrego.com.entrego.ui.main.home.model.DeliveryRequest
import entrego.com.entrego.util.Logger

/**
 * Created by bertalt on 12.12.16.
 */
class DrawerPresenter : IDrawerPresenter {

    val mDeliveryChangedListener = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(p0: Observable?, p1: Int) {

            when (p1) {
                BR.instance -> {
                    onBuildView()
                }
            }
        }
    }

    var view: IDrawerView? = null
    override fun onStart(view: IDrawerView) {

        this.view = view
        DeliveryInstance.getInstance().addOnPropertyChangedCallback(mDeliveryChangedListener)

        onBuildView()
    }

    fun onBuildView() {
        val delivery = DeliveryInstance.getInstance()
        Logger.logd(delivery.toString())

        when (delivery.customer) {
            null ->{ view?.showEmptyView()
            }
            else -> {
                view?.showDeliveryView()
//                if (delivery.customer != null) {
//                    view?.setupHeader(delivery.customer)
//                }
//                if (delivery.route != null) {
//                    view?.setupRoute(delivery.route)
//                }
            }
        }

    }

    override fun onStop() {
        DeliveryInstance.getInstance().removeOnPropertyChangedCallback(mDeliveryChangedListener)
        view = null
    }
}