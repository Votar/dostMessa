package entrego.com.android.ui.faq

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import entrego.com.android.databinding.ItemWeeklyUpdateBinding
import entrego.com.android.ui.score.updates.model.RatingModel

/**
 * Created by bertalt on 07.12.16.
 */
class UpdatesAdapter(list: List<RatingModel>, listener: RatingClickListener) : RecyclerView.Adapter<UpdatesAdapter.ViewHolder>() {

    val serviceList: List<RatingModel>
    val clickListener: RatingClickListener

    interface RatingClickListener {
        fun onItemClicked(item: RatingModel)
    }

    init {
        this.serviceList = list
        clickListener = listener
    }

    // Provide a reference to the views for each data item
// Complex data items may need more than one mView per item, and
// you provide access to all the views for a data item in a mView holder
    class ViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {

        var binder: ItemWeeklyUpdateBinding? = null

        init {
            binder = DataBindingUtil.bind(rootView)
        }
    }


    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val nextPoint = serviceList[position]
        holder?.binder?.rating = nextPoint
        holder?.binder?.weeklyUpdatesRoot?.setOnClickListener { clickListener.onItemClicked(nextPoint) }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): UpdatesAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val binding = ItemWeeklyUpdateBinding.inflate(inflater, parent, false)
        return ViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return serviceList.size
    }
}