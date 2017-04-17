package com.entregoya.msngr.ui.main.delivery.description

import android.databinding.DataBindingUtil
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.entregoya.msngr.R
import com.entregoya.msngr.binding.EntregoPointBinding
import com.entregoya.msngr.databinding.ItemDeliveryPointBinding
import com.entregoya.msngr.entity.EntregoWaypoint
import com.entregoya.msngr.storage.model.PointStatus
import com.entregoya.msngr.util.Logger


class PointsAdapter(val history: Array<EntregoWaypoint>) : RecyclerView.Adapter<PointsAdapter.ViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one mView per item, and
    // you provide access to all the views for a data item in a mView holder
    class ViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {

        var binder: ItemDeliveryPointBinding? = null

        init {
            binder = DataBindingUtil.bind(rootView)
        }


    }


    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

        var resId = R.drawable.points_list_next_pin

        val nextPoint = history[position]

        holder?.binder?.point = nextPoint

        when (nextPoint.status) {
            PointStatus.DONE -> resId = R.drawable.points_list_done_icon
            PointStatus.GOING, PointStatus.WAITING -> resId = R.drawable.start_pin
            PointStatus.PENDING -> resId = R.drawable.points_list_next_pin
            else -> resId = R.drawable.points_list_done_icon
        }
        val icon = ContextCompat.getDrawable(holder?.binder?.itemPointPin?.context, resId)

        holder?.binder?.itemPointPin?.setImageDrawable(icon)

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PointsAdapter.ViewHolder {

        val inflater = LayoutInflater.from(parent?.context)

        val binding = ItemDeliveryPointBinding.inflate(inflater, parent, false)

        return ViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return history.size
    }
}