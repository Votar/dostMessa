package entrego.com.entrego.ui.main.description

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.google.gson.Gson
import entrego.com.entrego.R
import entrego.com.entrego.storage.model.EntregoPoint
import entrego.com.entrego.ui.main.description.presenter.DescriptionPresenter
import entrego.com.entrego.ui.main.description.presenter.IDescriptionPresenter
import entrego.com.entrego.ui.main.description.view.IDescreptionView
import java.util.*

/**
 * Created by bertalt on 06.12.16.
 */
class DescriptionFragment : Fragment(), IDescreptionView {


    companion object {

        const val EXT_POINTS = "ext_points_key"

        fun getInstance(addresses: List<EntregoPoint>): DescriptionFragment {

            val gson = Gson()
            val resultList: Array<String?> = kotlin.arrayOfNulls<String>(addresses.size)

            for (next in 0..addresses.size - 1)
                resultList[next] = gson.toJson(addresses[next], EntregoPoint::class.java)

            val args = Bundle()
            args.putStringArray(EXT_POINTS, resultList)

            val fragment = DescriptionFragment()
            fragment.arguments = args
            return fragment
        }
    }

    var recycler: RecyclerView? = null
    var progress: ProgressBar? = null
    val presenter: IDescriptionPresenter = DescriptionPresenter()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater?.inflate(R.layout.fragment_description, container, false)

        recycler = view?.findViewById(R.id.descr_frag_points_recycler) as RecyclerView
        progress = view?.findViewById(R.id.addresses_progress) as ProgressBar
        return view
    }


    override fun onStart() {
        super.onStart()
        presenter.onStart(this)
        retainInstance = true
        if (arguments != null) {
            val pointsListJson = arguments.getStringArray(EXT_POINTS)
            val gson = Gson()
            val pointsListObject: ArrayList<EntregoPoint> = pointsListJson.mapTo(ArrayList()) { gson.fromJson(it, EntregoPoint::class.java) }
            presenter.requestAddresses(pointsListObject)
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

    override fun showFullDescription(listAddress: List<EntregoPoint>) {

        progress?.visibility = View.GONE
        recycler?.visibility = View.VISIBLE
        recycler?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recycler?.adapter = PointsAdapter(listAddress)

    }
}