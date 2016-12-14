package entrego.com.entrego.ui.main.delivery.description

import android.databinding.BindingAdapter
import android.databinding.DataBindingUtil
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import entrego.com.entrego.R
import entrego.com.entrego.databinding.ItemDeliveryPointBinding
import entrego.com.entrego.storage.model.EntregoPoint
import entrego.com.entrego.storage.model.EntregoPointStatus
import entrego.com.entrego.storage.model.EntregoRouteModel
import entrego.com.entrego.binding.EntregoPointBinding
import entrego.com.entrego.util.Logger
import java.util.*

/**
 * Created by bertalt on 07.12.16.
 */
class PointsAdapter(route: EntregoRouteModel) : RecyclerView.Adapter<PointsAdapter.ViewHolder>() {

    val addresses: List<EntregoPointBinding>

    init {

        addresses = listOf(route.start, route.destination)
        Logger.logd(addresses.toString())

    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    class ViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {

        var binder: ItemDeliveryPointBinding? = null
        init {
            binder = DataBindingUtil.bind(rootView)
        }


    }


    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

        var resId = R.drawable.points_list_next_pin

        val nextPoint = addresses[position]

        holder?.binder?.point = nextPoint

        when (nextPoint.status) {
            EntregoPointStatus.DONE.name -> resId = R.drawable.points_list_done_icon
            EntregoPointStatus.NEXT.name -> resId = R.drawable.start_pin
            EntregoPointStatus.SCHEDULE.name -> resId = R.drawable.points_list_next_pin
            else -> resId = R.drawable.points_list_done_icon
        }
        val icon = ContextCompat.getDrawable(holder?.binder?.itemPointPin?.context, resId)

        holder?.binder?.itemPointPin?.setImageDrawable(icon)

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PointsAdapter.ViewHolder {

        val inflater = LayoutInflater.from(parent?.context)

        val binding = ItemDeliveryPointBinding.inflate(inflater, parent, false)


        // set the view's size, margins, paddings and layout parameters


        return ViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return addresses.size
    }
}