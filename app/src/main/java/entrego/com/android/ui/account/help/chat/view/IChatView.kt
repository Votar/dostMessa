package entrego.com.android.ui.account.help.chat.view

import android.content.Context
import entrego.com.android.ui.account.help.chat.model.ChatMessage

/**
 * Created by bertalt on 08.12.16.
 */
interface IChatView {

    fun showLoadingProgress()
    fun hideLoadingProgress()
    fun getChatContext() :Context
    fun showMessage(message: ChatMessage)
}