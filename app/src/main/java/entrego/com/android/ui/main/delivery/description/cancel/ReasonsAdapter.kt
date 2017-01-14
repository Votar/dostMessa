package entrego.com.android.ui.main.delivery.description.cancel

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import entrego.com.android.databinding.ItemCancelReasonBinding

/**
 * Created by bertalt on 07.12.16.
 */
class ReasonsAdapter(reasons: List<String>, listener: OnReasonClicked) : RecyclerView.Adapter<ReasonsAdapter.ViewHolder>() {

    val reasons: List<String>
    val listener: OnReasonClicked

    init {
        this.reasons = reasons
        this.listener = listener
    }

    interface OnReasonClicked {
        fun onClickedReason(reason: String)
    }
    // Provide a reference to the views for each data item
    // Complex data items may need more than one mView per item, and
    // you provide access to all the views for a data item in a mView holder
    class ViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {

        var binder: ItemCancelReasonBinding? = null

        init {
            binder = DataBindingUtil.bind(rootView)
        }
    }


    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val nextReason = reasons[position]
        holder?.binder?.reason = nextReason
        holder?.binder?.onReasonListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val binding = ItemCancelReasonBinding.inflate(inflater, parent, false)
        // set the mView's size, margins, paddings and layout parameters
        return ViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return reasons.size
    }
}