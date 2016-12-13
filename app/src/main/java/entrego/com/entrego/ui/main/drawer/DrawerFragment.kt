package entrego.com.entrego.ui.main.drawer

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import entrego.com.entrego.R
import entrego.com.entrego.databinding.FragmentDescriptionBinding
import entrego.com.entrego.databinding.FragmentDrawerBinding
import entrego.com.entrego.storage.model.CustomerModel
import entrego.com.entrego.storage.model.EntregoRouteModel
import entrego.com.entrego.storage.model.binding.DeliveryInstance
import entrego.com.entrego.ui.main.drawer.presenter.DrawerPresenter
import entrego.com.entrego.ui.main.drawer.presenter.IDrawerPresenter
import entrego.com.entrego.ui.main.drawer.view.IDrawerView
import kotlinx.android.synthetic.main.fragment_drawer.*

/**
 * Created by bertalt on 09.12.16.
 */
class DrawerFragment : Fragment(), IDrawerView {

    var presenter: IDrawerPresenter = DrawerPresenter()
    var binder: FragmentDrawerBinding? = null
    override fun showEmptyView() {

//        drawer_delivery_ll?.visibility = View.GONE

        binder?.invalidateAll()
    }

    override fun showDeliveryView() {

//        drawer_delivery_ll?.visibility = View.VISIBLE
        binder?.invalidateAll()
    }


    override fun setupHeader(customer: CustomerModel?) {


    }

    override fun setupRoute(route: EntregoRouteModel?) {

    }


    override fun onStart() {
        super.onStart()
        presenter.onStart(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        retainInstance = true
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_drawer, container, false)
        binder?.delivery = DeliveryInstance.getInstance()

        return binder?.root
    }

}