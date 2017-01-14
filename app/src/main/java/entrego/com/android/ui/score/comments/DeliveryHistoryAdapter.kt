package entrego.com.android.ui.score.comments

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import entrego.com.android.databinding.CommentsItemRecyclerBinding

class CommentsAdapter() : RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one mView per item, and
    // you provide access to all the views for a data item in a mView holder
    class ViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {
        var binder: CommentsItemRecyclerBinding? = null
        init {
            binder = DataBindingUtil.bind(rootView)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.binder?.comment = "debug"
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val binding = CommentsItemRecyclerBinding.inflate(inflater, parent, false)
        return ViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return 4
    }
}