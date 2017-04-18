package com.entregoya.msngr.ui.account.help.reports.model

import android.databinding.DataBindingUtil
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.entregoya.msngr.R
import com.entregoya.msngr.databinding.ReportsListItemBinding
import com.entregoya.msngr.ui.account.help.reports.model.ReportEntity

class ReportsAdapter(val reportList: List<ReportEntity>,
                     val clickListener: ReportClickListener) : RecyclerView.Adapter<ReportsAdapter.ViewHolder>() {

    interface ReportClickListener {
        fun onReportClicked(item: ReportEntity)
    }


    // Provide a reference to the views for each data item
// Complex data items may need more than one mView per item, and
// you provide access to all the views for a data item in a mView holder
    class ViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {

        var binder: ReportsListItemBinding? = null

        init {
            binder = DataBindingUtil.bind(rootView)
        }
    }


    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val nextPoint = reportList[position]
        holder?.binder?.report = nextPoint

        val resolvedColor = ContextCompat.getColor(holder?.binder?.root?.context, R.color.colorDarkBlue)

        holder?.binder?.root?.setOnClickListener { clickListener.onReportClicked(nextPoint) }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val binding = ReportsListItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return reportList.size
    }
}