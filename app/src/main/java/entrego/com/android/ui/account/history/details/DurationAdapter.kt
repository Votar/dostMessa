package entrego.com.android.ui.account.history.details

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import entrego.com.android.R


class DurationAdapter(val dataset: List<String>) : RecyclerView.Adapter<DurationAdapter.ViewHolder>() {

    class ViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {

        var label: TextView? = null
        var address: TextView? = null

        init {
            label = rootView.findViewById(R.id.item_route_label) as TextView
            address = rootView.findViewById(R.id.item_route_address) as TextView
        }
    }


    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val nextPoint = dataset[position]
        if (position != 0)
            holder?.label?.visibility = View.INVISIBLE

        holder?.address?.text = nextPoint
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DurationAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val view = inflater.inflate(R.layout.item_history_delivery_duration, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataset.count()
    }
}