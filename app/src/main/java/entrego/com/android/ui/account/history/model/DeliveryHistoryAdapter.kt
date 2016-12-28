package entrego.com.android.ui.account.history.model

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.content.res.AppCompatResources
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import entrego.com.android.R
import entrego.com.android.databinding.ItemHistoryRoutesBinding

/**
 * Created by bertalt on 07.12.16.
 */
class DeliveryHistoryAdapter(val context: Context?) : RecyclerView.Adapter<DeliveryHistoryAdapter.ViewHolder>() {


    init {

    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    class ViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {

        var binder: ItemHistoryRoutesBinding? = null

        init {
            binder = DataBindingUtil.bind(rootView)
        }


    }

    val url = "http://maps.googleapis.com/maps/api/staticmap?center=Albany,+NY&zoom=13&scale=false&size=600x300&maptype=roadmap&format=png&visual_refresh=true"

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

        holder?.binder?.debugText = "debug"

        val ctx = holder?.itemView?.context
        if (ctx != null) {

            val loadingIcon = AppCompatResources.getDrawable(ctx, R.drawable.ic_cloud_download_50dp)
            if (context != null)
                Glide.with(context)
                        .load(url)
                        .into(holder?.binder?.historyRoutesStaticMap)
                        .onLoadStarted(loadingIcon)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DeliveryHistoryAdapter.ViewHolder {

        val inflater = LayoutInflater.from(parent?.context)

        val binding = ItemHistoryRoutesBinding.inflate(inflater, parent, false)


        return ViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return 4
    }
}