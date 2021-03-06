package com.entregoya.msngr.ui.main.drawer

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.SwitchCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.entregoya.msngr.R
import com.entregoya.msngr.binding.Delivery
import com.entregoya.msngr.databinding.FragmentDrawerBinding
import com.entregoya.msngr.storage.model.PointStatus
import com.entregoya.msngr.storage.preferences.EntregoStorage
import com.entregoya.msngr.ui.main.drawer.presenter.DrawerPresenter
import com.entregoya.msngr.ui.main.drawer.presenter.IDrawerPresenter
import com.entregoya.msngr.ui.main.drawer.shopping.AcceptShoppingActivity
import com.entregoya.msngr.ui.main.drawer.view.IDrawerView
import com.entregoya.msngr.ui.main.drawer.sign.SignActivity
import com.entregoya.msngr.util.snackSimple
import kotlinx.android.synthetic.main.fragment_drawer.*
import kotlinx.android.synthetic.main.status_service_delivered.*
import kotlinx.android.synthetic.main.status_service_on_the_way.*
import kotlinx.android.synthetic.main.status_service_ship_on_way.*
import kotlinx.android.synthetic.main.status_service_waiting_for_delivery.*
import kotlinx.android.synthetic.main.status_service_waiting_for_ship.*

class DrawerFragment : Fragment(), IDrawerView {



    var presenter: IDrawerPresenter = DrawerPresenter()
    var binder: FragmentDrawerBinding? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        retainInstance = true
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_drawer, container, false)
        binder?.delivery = Delivery.getInstance()
        return binder?.root
    }

    override fun onStart() {
        super.onStart()
        val token = EntregoStorage.getToken()
        presenter.onStart(this, token)
        delivery_states_on_way_sw.setOnCheckedChangeListener { button, state ->
            if (state) {
                presenter.changeStatus(button as SwitchCompat, PointStatus.GOING)
                button.isEnabled = false
            }
        }
        delivery_states_waiting_for_ship_sw.setOnCheckedChangeListener { button, state ->
            if (state) {
                presenter.changeStatus(button as SwitchCompat, PointStatus.WAITING)
                button.isEnabled = false
            }
        }
        delivery_states_ship_on_way_sw.setOnCheckedChangeListener { button, state ->
            if (state) {
                presenter.changeStatus(button as SwitchCompat, PointStatus.GOING)
                button.isEnabled = false
            }
        }
        delivery_states_waiting_for_delivery_sw.setOnCheckedChangeListener { button, state ->
            if (state) {
                presenter.changeStatus(button as SwitchCompat, PointStatus.WAITING)
                button.isEnabled = false
            }
        }
        delivery_states_delivered_sw.setOnCheckedChangeListener { button, state ->
            if (state) {
                presenter.changeStatus(button as SwitchCompat, PointStatus.DONE)
                button.isEnabled = false
            }
        }
        drawer_sign_bill.setOnClickListener { startSignActivity() }

        drawer_purchase.setOnClickListener { presenter.sendShoppingReceipt() }
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    override fun buildMultiDelivery() {

    }

    override fun refreshView() {
        binder?.invalidateAll()
        binder?.executePendingBindings()
        if (Delivery.getInstance().history != null)
            presenter.buildSwitchListByState(Delivery.getInstance().history, getSwitchList())
    }

    override fun showMessage(message: String?) {
        view?.snackSimple(message)
    }

    fun getSwitchList(): List<SwitchCompat> =
            listOf(
                    delivery_states_on_way_sw,
                    delivery_states_waiting_for_ship_sw,
                    delivery_states_ship_on_way_sw,
                    delivery_states_waiting_for_delivery_sw,
                    delivery_states_delivered_sw)


    fun startSignActivity() {
        val intent = Intent(context, SignActivity::class.java)
        startActivity(intent)
    }

    override fun startShoppingProcedure() {
        val orderId = Delivery.getInstance().id
        if (orderId != -1L)
            startActivity(AcceptShoppingActivity.getIntent(activity, orderId))
        else
            throw IllegalStateException("No avaliable order id")
    }
}