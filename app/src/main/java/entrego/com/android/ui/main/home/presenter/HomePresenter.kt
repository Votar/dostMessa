package entrego.com.android.ui.main.home.presenter

import android.databinding.Observable
import android.os.Handler
import android.text.TextUtils
import entrego.com.android.BR
import entrego.com.android.location.diraction.DirectionFinder
import entrego.com.android.location.diraction.DirectionFinderListener
import entrego.com.android.location.diraction.Route
import entrego.com.android.binding.DeliveryInstance
import entrego.com.android.ui.main.home.view.IHomeView
import entrego.com.android.util.toDirectionFormat

/**
 * Created by bertalt on 06.12.16.
 */
class HomePresenter : IHomePresenter {

    var view: IHomeView? = null
    val mDeliveryChangedListener = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(p0: Observable?, p1: Int) {
            when (p1) {
                BR.instance -> {
                    onBuildView()
                }
            }
        }
    }

    override fun onStart(view: IHomeView) {
        this.view = view
        DeliveryInstance.getInstance().addOnPropertyChangedCallback(mDeliveryChangedListener)
        onBuildView()
    }

    fun onBuildView() {
        val delivery = DeliveryInstance.getInstance()
        if (delivery.route != null) {
            view?.prepareRoute(delivery.route)
            if (!TextUtils.isEmpty(delivery.route.path.line)) {
                view?.buildPath(delivery.route.path.line)
            }
        } else {
          view?.prepareNoDelivery()
        }
    }

    override fun onStop() {

        DeliveryInstance.getInstance().removeOnPropertyChangedCallback(mDeliveryChangedListener)
        view = null
    }

}