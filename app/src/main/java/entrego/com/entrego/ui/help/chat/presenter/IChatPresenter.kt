package entrego.com.entrego.ui.help.chat.presenter

import android.support.v7.widget.RecyclerView
import entrego.com.entrego.ui.help.chat.model.ChatMessage
import entrego.com.entrego.ui.help.chat.view.IChatView

/**
 * Created by bertalt on 08.12.16.
 */
interface IChatPresenter {

    fun onCreate(view: IChatView)
    fun onDestroy()
    fun showMessage(message: ChatMessage)
    fun sendMessage(message: String)

}