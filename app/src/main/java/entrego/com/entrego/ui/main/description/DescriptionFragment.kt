package entrego.com.entrego.ui.main.description

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.google.gson.Gson
import entrego.com.entrego.R
import entrego.com.entrego.databinding.FragmentDescriptionBinding
import entrego.com.entrego.storage.model.DeliveryModel
import entrego.com.entrego.storage.model.EntregoPoint
import entrego.com.entrego.storage.model.binding.DeliveryInstance
import entrego.com.entrego.storage.model.binding.EntregoPointBinding
import entrego.com.entrego.ui.main.description.presenter.DescriptionPresenter
import entrego.com.entrego.ui.main.description.presenter.IDescriptionPresenter
import entrego.com.entrego.ui.main.description.view.IDescreptionView
import entrego.com.entrego.util.Logger
import java.util.*

/**
 * Created by bertalt on 06.12.16.
 */
class DescriptionFragment : Fragment(), IDescreptionView {


    companion object {


        fun getInstance(): DescriptionFragment {

            val fragment = DescriptionFragment()
            return fragment
        }
    }

    var recycler: RecyclerView? = null
    var progress: ProgressBar? = null
    val presenter: IDescriptionPresenter = DescriptionPresenter()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        retainInstance = true
        val binder: FragmentDescriptionBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_description, container, false)
        binder.delivery = DeliveryInstance.getInstance()
        val view = binder.root

        recycler = view.findViewById(R.id.descr_frag_points_recycler) as RecyclerView
        progress = view.findViewById(R.id.addresses_progress) as ProgressBar

        return view

    }

    override fun onDestroyView() {
        super.onDestroyView()
        Logger.logd("FragmentView have been destroyed")
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart(this)
        val delivery = DeliveryInstance.getInstance()
        if (delivery != null) {

            Logger.logd("delivery start")

            recycler?.visibility = View.VISIBLE
            recycler?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            recycler?.adapter = PointsAdapter(delivery.route)
            presenter.requestAddresses(delivery.route)
        }
    }


    override fun getViewContext(): Context {
        return context
    }

    override fun showLoadingPoints() {
        progress?.visibility = View.VISIBLE
        recycler?.visibility = View.GONE
    }

    override fun showEmptyView() {

    }

    override fun showFullDescription(listAddress: List<EntregoPointBinding>) {

//        progress?.visibility = View.GONE
//        recycler?.visibility = View.VISIBLE
//        recycler?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

    }
}