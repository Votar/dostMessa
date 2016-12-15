package entrego.com.android.ui.main.home.presenter

import android.databinding.Observable
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
                BR.path -> {
                    val path = DeliveryInstance.getInstance().path
                    view?.buildPath(path)
                }
            }
        }
    }

    override fun onStart(view: IHomeView) {

        this.view = view
        onBuildView()
        DeliveryInstance.getInstance().addOnPropertyChangedCallback(mDeliveryChangedListener)

    }


    fun onBuildView() {

        val delivery = DeliveryInstance.getInstance()



        if (delivery.route != null) {
            view?.prepareRoute(delivery.route)

            if (delivery.path != null)
                view?.buildPath(delivery.path)
            else {
                DirectionFinder(directionListener,
                        delivery.route.start.toDirectionFormat(),
                        delivery.route.destination.toDirectionFormat(),
                        null)
                        .execute()
            }
        } else
            view?.prepareNoDelivery()

    }

    override fun onStop() {

        DeliveryInstance.getInstance().removeOnPropertyChangedCallback(mDeliveryChangedListener)
        view = null
    }

    val directionListener = object : DirectionFinderListener {
        override fun onDirectionFinderStart() {

        }

        override fun onDirectionFinderSuccess(route: MutableList<Route>?) {

            if (route != null && route.size > 0)
                view?.buildPath(route[0])
        }

    }
}