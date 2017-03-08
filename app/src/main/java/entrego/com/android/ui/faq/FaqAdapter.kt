package entrego.com.android.ui.faq

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import entrego.com.android.databinding.FaqListItemBinding

/**
 * Created by bertalt on 07.12.16.
 */
class FaqAdapter(faqList: List<Pair<String, String>>, listener: FaqClickListener) : RecyclerView.Adapter<FaqAdapter.ViewHolder>() {

    val faqList: List<Pair<String, String>>
    val onFaqClick: FaqClickListener

    interface FaqClickListener {
        fun onFaqClicked(title: String, message: String)
    }

    init {
        this.faqList = faqList
        onFaqClick = listener
    }

    // Provide a reference to the views for each data item
// Complex data items may need more than one mView per item, and
// you provide access to all the views for a data item in a mView holder
    class ViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {

        var binder: FaqListItemBinding? = null

        init {
            binder = DataBindingUtil.bind(rootView)
        }
    }


    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val nextPoint = faqList[position]
        holder?.binder?.faqTitle = nextPoint.first
        holder?.binder?.faqRootView?.setOnClickListener { onFaqClick.onFaqClicked(nextPoint.first, nextPoint.second) }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): FaqAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val binding = FaqListItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return faqList.size
    }
}