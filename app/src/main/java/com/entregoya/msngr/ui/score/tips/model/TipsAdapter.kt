package com.entregoya.msngr.ui.faq

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.entregoya.msngr.databinding.ItemTipsListBinding

class TipsAdapter(val dataset: List<String>) : RecyclerView.Adapter<TipsAdapter.ViewHolder>() {

    // Provide a reference to the views for each data item
// Complex data items may need more than one mView per item, and
// you provide access to all the views for a data item in a mView holder
    class ViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {
        var binder: ItemTipsListBinding? = null
        init {
            binder = DataBindingUtil.bind(rootView)
        }
    }


    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val nextPoint = dataset[position]
        holder?.binder?.tip = nextPoint
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TipsAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val binding = ItemTipsListBinding.inflate(inflater, parent, false)
        return ViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}