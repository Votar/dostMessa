package entrego.com.android.ui.faq

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import entrego.com.android.databinding.ItemHistoryServiceBinding
import entrego.com.android.entity.HistoryServicesPreviewEntity


class HistoryServiceAdapter(list: List<HistoryServicesPreviewEntity>, listener: HistoryServiceClickListener) : RecyclerView.Adapter<HistoryServiceAdapter.ViewHolder>() {

    val serviceList: List<HistoryServicesPreviewEntity>
    val clickListener: HistoryServiceClickListener

    interface HistoryServiceClickListener {
        fun onHistoryClicked(item: HistoryServicesPreviewEntity)
    }

    init {
        this.serviceList = list
        clickListener = listener
    }

    // Provide a reference to the views for each data item
// Complex data items may need more than one mView per item, and
// you provide access to all the views for a data item in a mView holder
    class ViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {

        var binder: ItemHistoryServiceBinding? = null

        init {
            binder = DataBindingUtil.bind(rootView)
        }
    }


    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val nextPoint = serviceList[position]
        holder?.binder?.service = nextPoint
        holder?.binder?.historyServiceItemRoot?.setOnClickListener { clickListener.onHistoryClicked(nextPoint) }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): HistoryServiceAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val binding = ItemHistoryServiceBinding.inflate(inflater, parent, false)
        return ViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return serviceList.size
    }
}