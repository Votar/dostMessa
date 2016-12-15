package entrego.com.android.ui.account.help.chat.presenter

import android.support.v7.widget.RecyclerView
import entrego.com.android.ui.account.help.chat.model.ChatMessage
import entrego.com.android.ui.account.help.chat.view.IChatView

/**
 * Created by bertalt on 08.12.16.
 */
interface IChatPresenter {

    fun onCreate(view: IChatView)
    fun onDestroy()
    fun showMessage(message: ChatMessage)
    fun sendMessage(message: String)

}