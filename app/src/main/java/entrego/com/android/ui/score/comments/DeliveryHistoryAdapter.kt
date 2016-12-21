package entrego.com.android.ui.account.history.model

import android.content.Context
import android.databinding.BindingAdapter
import android.databinding.DataBindingUtil
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import entrego.com.android.R
import entrego.com.android.databinding.ItemDeliveryPointBinding
import entrego.com.android.storage.model.EntregoPoint
import entrego.com.android.storage.model.EntregoPointStatus
import entrego.com.android.storage.model.EntregoRouteModel
import entrego.com.android.binding.EntregoPointBinding
import entrego.com.android.databinding.CommentsItemRecyclerBinding
import entrego.com.android.databinding.ItemHistoryRoutesBinding
import entrego.com.android.ui.main.delivery.description.PointsAdapter
import entrego.com.android.util.Logger
import java.util.*

class CommentsAdapter() : RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    class ViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {

        var binder: CommentsItemRecyclerBinding? = null

        init {
            binder = DataBindingUtil.bind(rootView)
        }


    }

    val url = "http://maps.googleapis.com/maps/api/staticmap?center=Albany,+NY&zoom=13&scale=false&size=600x300&maptype=roadmap&format=png&visual_refresh=true"

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

        holder?.binder?.comment = "debug"

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CommentsAdapter.ViewHolder {

        val inflater = LayoutInflater.from(parent?.context)

        val binding = CommentsItemRecyclerBinding.inflate(inflater, parent, false)


        return ViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return 4
    }
}