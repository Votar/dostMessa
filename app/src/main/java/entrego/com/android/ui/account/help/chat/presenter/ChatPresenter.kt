package entrego.com.android.ui.account.help.chat.presenter

import android.support.v7.widget.RecyclerView
import entrego.com.android.ui.account.help.chat.model.ChatConnection
import entrego.com.android.ui.account.help.chat.model.ChatMessage
import entrego.com.android.ui.account.help.chat.view.IChatView

/**
 * Created by bertalt on 08.12.16.
 */
class ChatPresenter : IChatPresenter {

    var view: IChatView? = null
    var connection: ChatConnection? = null

    override fun sendMessage(message: String) {
        connection?.postMessage(message)
    }

    override fun onCreate(view: IChatView) {
        this.view = view
        connection = ChatConnection(this)

    }

    override fun onDestroy() {
        view = null
    }

    override fun showMessage(message: ChatMessage) {
        view?.showMessage(message)
    }
}