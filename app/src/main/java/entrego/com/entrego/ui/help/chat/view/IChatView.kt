package entrego.com.entrego.ui.help.chat.view

import android.content.Context
import entrego.com.entrego.ui.help.chat.model.ChatMessage

/**
 * Created by bertalt on 08.12.16.
 */
interface IChatView {

    fun showLoadingProgress()
    fun hideLoadingProgress()
    fun getChatContext() :Context
    fun showMessage(message: ChatMessage)
}