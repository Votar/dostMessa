package entrego.com.entrego.ui.main.description

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import entrego.com.entrego.R
import entrego.com.entrego.storage.model.EntregoPoint
import entrego.com.entrego.storage.model.EntregoPointStatus
import java.util.*

/**
 * Created by bertalt on 07.12.16.
 */
class PointsAdapter(var addresses: List<EntregoPoint>) : RecyclerView.Adapter<PointsAdapter.ViewHolder>() {


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    class ViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {
        var address: TextView? = null
        var pointPin: ImageView? = null
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

        var resId = R.drawable.points_list_next_pin

        val nextPoint = addresses[position]

        when (nextPoint.status) {
            EntregoPointStatus.DONE.name -> resId = R.drawable.points_list_done_icon
            EntregoPointStatus.NEXT.name -> resId = R.drawable.start_pin
            EntregoPointStatus.SCHEDULE.name -> resId = R.drawable.points_list_next_pin
        }
        val icon = ContextCompat.getDrawable(holder?.pointPin?.context, resId)

        holder?.pointPin?.setImageDrawable(icon)
        holder?.address?.text = nextPoint.address

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PointsAdapter.ViewHolder {

        val v = LayoutInflater.from(parent?.context)
                .inflate(R.layout.item_delivery_point, parent, false)

        // set the view's size, margins, paddings and layout parameters
        val vh = ViewHolder(v)
        vh.pointPin = v.findViewById(R.id.item_point_pin) as ImageView
        vh.address = v.findViewById(R.id.item_address) as TextView

        return vh
    }

    override fun getItemCount(): Int {
        return addresses.size
    }
}