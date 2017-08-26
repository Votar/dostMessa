package com.entregoya.msngr.ui.main.delivery.description

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.entregoya.msngr.R
import com.entregoya.msngr.binding.Delivery
import com.entregoya.msngr.databinding.FragmentDescriptionBinding
import com.entregoya.msngr.ui.account.profile.UserProfile
import com.entregoya.msngr.ui.main.delivery.description.cancel.CancelDeliveryActivity
import com.entregoya.msngr.ui.main.delivery.description.chat.ChatMessengerActivity
import com.entregoya.msngr.ui.main.delivery.description.presenter.DescriptionPresenter
import com.entregoya.msngr.ui.main.delivery.description.presenter.IDescriptionPresenter
import com.entregoya.msngr.ui.main.delivery.description.view.IDescreptionView
import com.entregoya.msngr.util.Logger
import kotlinx.android.synthetic.main.fragment_description.*

class DescriptionFragment : Fragment(), IDescreptionView {

    companion object {
        val TAG = "DescriptionFragmentTAG"
        fun getInstance(): DescriptionFragment {
            val fragment = DescriptionFragment()
            return fragment
        }
    }

    var recycler: RecyclerView? = null
    val presenter: IDescriptionPresenter = DescriptionPresenter()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        retainInstance = true
        val binder: FragmentDescriptionBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_description, container, false)
        binder.delivery = Delivery.getInstance()
        val view = binder.root
        recycler = view.findViewById(R.id.descr_frag_points_recycler) as RecyclerView
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Logger.logd("FragmentView have been destroyed")
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart(this)
        val delivery = Delivery.getInstance()
        if (delivery.history != null) {
            Logger.logd("delivery start")
            recycler?.visibility = View.VISIBLE
            recycler?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            recycler?.adapter = PointsAdapter(delivery.history.value)
        }
        fragment_descr_cancel.setOnClickListener {
            val intent = Intent(context, CancelDeliveryActivity::class.java)
            startActivity(intent)
        }

        descr_frag_chat_rl.setOnClickListener {
            val deliverId = Delivery.getInstance().id
            val userId = UserProfile.getProfile()?.id
            userId?.let {
                val intent = ChatMessengerActivity.getIntent(activity, deliverId, it)
                activity.startActivity(intent)
            }
        }




        descr_call_cusomer_btn.setOnClickListener { presenter.callCustomer() }
    }


    override fun showMessage(resId: Int) {
        Toast.makeText(activity, getString(resId), Toast.LENGTH_LONG).show()
    }

    override fun getViewContext(): Context {
        return context
    }

    override fun showEmptyView() {

    }

}