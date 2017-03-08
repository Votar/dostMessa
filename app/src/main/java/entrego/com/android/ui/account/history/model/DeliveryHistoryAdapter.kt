package entrego.com.android.ui.account.history.model

import android.databinding.DataBindingUtil
import android.support.v7.content.res.AppCompatResources
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import entrego.com.android.R
import entrego.com.android.databinding.ItemHistoryRoutesBinding
import entrego.com.android.entity.EntregoWaypoint
import entrego.com.android.storage.model.DeliveryModel
import entrego.com.android.util.Logger
import entrego.com.android.util.getStaticMapUrl

class DeliveryHistoryAdapter(val dataset: Array<DeliveryModel>, val listener: ClickItemListener) : RecyclerView.Adapter<DeliveryHistoryAdapter.ViewHolder>() {

    interface ClickItemListener {
        fun onItemClicked(delivery: DeliveryModel)
    }

    class ViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {
        var binder: ItemHistoryRoutesBinding? = null

        init {
            binder = DataBindingUtil.bind(rootView)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

        val currentModel = dataset[position]
        holder?.binder?.model = currentModel

        val ctx = holder?.itemView?.context
        if (ctx != null) {
            val url = buildUrlForStaticMap(currentModel.path.line, currentModel.history)
            Logger.logd(url)
            val loadingIcon = AppCompatResources.getDrawable(ctx, R.drawable.ic_cloud_download_50dp)
            Glide.with(ctx)
                    .load(url)
                    .error(R.drawable.ic_cloud_off_48dp)
                    .into(holder?.binder?.historyRoutesStaticMap)

        }
        holder?.binder?.root?.setOnClickListener { listener.onItemClicked(currentModel) }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DeliveryHistoryAdapter.ViewHolder {

        val inflater = LayoutInflater.from(parent?.context)
        val binding = ItemHistoryRoutesBinding.inflate(inflater, parent, false)
        return ViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return dataset.count()
    }

    fun buildUrlForStaticMap(path: String, history: Array<EntregoWaypoint>): String {
        return history.getStaticMapUrl(path)
    }
}