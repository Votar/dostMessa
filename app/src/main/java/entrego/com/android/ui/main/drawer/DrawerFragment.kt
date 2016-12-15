package entrego.com.android.ui.main.drawer

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import entrego.com.android.R
import entrego.com.android.databinding.FragmentDescriptionBinding
import entrego.com.android.databinding.FragmentDrawerBinding
import entrego.com.android.storage.model.CustomerModel
import entrego.com.android.storage.model.EntregoRouteModel
import entrego.com.android.binding.DeliveryInstance
import entrego.com.android.ui.main.drawer.presenter.DrawerPresenter
import entrego.com.android.ui.main.drawer.presenter.IDrawerPresenter
import entrego.com.android.ui.main.drawer.view.IDrawerView
import entrego.com.android.ui.sign.SignActivity
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
        drawer_sign_bill.setOnClickListener {
            startSignActivity()
        }
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

    fun startSignActivity() {
        val intent = Intent(context, SignActivity::class.java)
        startActivity(intent)
    }


}