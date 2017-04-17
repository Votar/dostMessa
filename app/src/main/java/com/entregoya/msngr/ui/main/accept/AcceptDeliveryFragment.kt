package com.entregoya.msngr.ui.main.accept

import android.app.ProgressDialog
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.entregoya.msngr.R
import com.entregoya.msngr.binding.Delivery
import com.entregoya.msngr.databinding.FragmentAcceptDeliveryBinding
import com.entregoya.msngr.storage.preferences.EntregoStorage
import com.entregoya.msngr.ui.main.accept.presenter.AcceptDeliveryPresenter
import com.entregoya.msngr.ui.main.accept.presenter.IAcceptDeliveryPresenter
import com.entregoya.msngr.ui.main.accept.view.IAcceptDeliveryView
import com.entregoya.msngr.util.loading
import com.entregoya.msngr.util.showSnack
import com.entregoya.msngr.util.snackSimple


class AcceptDeliveryFragment : Fragment(), IAcceptDeliveryView {


    companion object {
        val TAG = "AcceptDeliveryFragment"
        fun show(supportFragmentManager: FragmentManager) {

            val current = supportFragmentManager.findFragmentByTag(TAG)
            if (current == null) {
                val fragment = AcceptDeliveryFragment()
                supportFragmentManager.beginTransaction()
                        .add(R.id.root_accept_container, fragment, TAG)
                        .commit()
            }
        }

        fun dismiss(supportFragmentManager: FragmentManager) {
            supportFragmentManager.findFragmentByTag(TAG)?.apply {
                supportFragmentManager.beginTransaction()
                        .remove(this)
                        .commit()
            }
        }
    }

    val mPresenter: IAcceptDeliveryPresenter = AcceptDeliveryPresenter()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binder: FragmentAcceptDeliveryBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_accept_delivery, container, false)
        binder.delivery = Delivery.getInstance()
        binder.presenter = mPresenter
        return binder.root
    }

    override fun onStart() {
        super.onStart()
        mPresenter.onStart(this)
    }

    override fun onStop() {
        super.onStop()
        mPresenter.onStop()
    }

    override fun hideProgress() {
        progress?.dismiss()
    }

    var progress: ProgressDialog? = null
    override fun showProgress() {
        progress = ProgressDialog(context)
        progress?.loading()
    }

    override fun getToken(): String {
        return EntregoStorage.getToken()
    }

    override fun hideAcceptFragment() {
        dismiss(activity.supportFragmentManager)
    }

    override fun showMessage(message: String?) {
        view?.snackSimple(message)
    }

    override fun showMessage(stringId: Int) {
        view?.snackSimple(getString(stringId))
    }
}