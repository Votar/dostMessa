package com.entregoya.msngr.ui.main.delivery.description.chat.model.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.entregoya.msngr.ui.main.delivery.description.chat.model.ChatMessageEntity
import com.entregoya.msngr.ui.main.delivery.description.chat.model.UserType
import com.entregoya.msngr.R
import com.entregoya.msngr.util.Logger.logd
import java.text.SimpleDateFormat
import java.util.*

class ChatThreadAdapter : RecyclerView.Adapter<ChatThreadAdapter.ViewHolder>() {

    val dataset = LinkedList<ChatMessageEntity>()

    class ViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {

        internal var tvTimestamp: TextView? = null
        internal var tvMessage: TextView? = null

        init {
            tvMessage = rootView.findViewById(R.id.message) as TextView
            tvTimestamp = rootView.findViewById(R.id.timestamp) as TextView
        }
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

        val currentModel = dataset[position]

        //format timestamp
        val timestamp = currentModel.timestamp.getFormattedTimestamp()
        holder?.tvTimestamp?.text = timestamp
        holder?.tvMessage?.text = currentModel.text
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        if (viewType == UserType.SELF.value) {
            val view = LayoutInflater.from(parent?.context)
                    .inflate(R.layout.item_chat_self, parent, false)
            return ViewHolder(view)
        }

        val view = LayoutInflater.from(parent?.context)
                .inflate(R.layout.item_chat_other, parent, false)
        return ViewHolder(view)
    }

    fun addMessage(message: ChatMessageEntity) {
        dataset.add(message)
        logd("Message added" + message.text)
        notifyDataSetChanged()
    }
    fun addMessages(listMessages: List<ChatMessageEntity>){
        dataset.clear()
        dataset.addAll(listMessages)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int = dataset.get(position).userType.value

    override fun getItemCount(): Int = dataset.count()

    private fun getTimeStamp(dateStr: Date): String {

        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val timestamp = format.format(dateStr)
        return timestamp
    }

    private fun Long.getFormattedTimestamp(): String {


        val date = Date(this*1000)
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val timestamp = format.format(date)
        return timestamp
    }
}