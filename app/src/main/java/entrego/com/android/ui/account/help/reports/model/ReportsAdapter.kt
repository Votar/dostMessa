package entrego.com.android.ui.faq

import android.content.res.Resources
import android.databinding.DataBindingUtil
import android.support.v4.content.ContextCompat
import android.support.v7.content.res.AppCompatResources
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import entrego.com.android.R
import entrego.com.android.binding.HistoryServiceBinding
import entrego.com.android.databinding.ItemHistoryServiceBinding
import entrego.com.android.databinding.ReportsListItemBinding
import entrego.com.android.ui.account.help.reports.model.ReportEntity

/**
 * Created by bertalt on 07.12.16.
 */
class ReportsAdapter(list: List<ReportEntity>, listener: ReportClickListener) : RecyclerView.Adapter<ReportsAdapter.ViewHolder>() {

    val reportList: List<ReportEntity>
    val clickListener: ReportClickListener

    interface ReportClickListener {
        fun onReportClicked(item: ReportEntity)
    }

    init {
        this.reportList = list
        clickListener = listener
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

        if (nextPoint.status.contentEquals("Resolved"))
            holder?.binder?.reportsListStatusFl?.setBackgroundColor(resolvedColor)

        holder?.binder?.root?.setOnClickListener { clickListener.onReportClicked(nextPoint) }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ReportsAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val binding = ReportsListItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return reportList.size
    }
}