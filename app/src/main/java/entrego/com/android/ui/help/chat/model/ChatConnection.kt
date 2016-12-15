package entrego.com.android.ui.help.chat.model

import android.os.Handler
import android.os.Looper
import entrego.com.android.ui.help.chat.presenter.IChatPresenter

/**
 * Created by bertalt on 08.12.16.
 */
class ChatConnection(val presenter: IChatPresenter?) {

    fun postMessage(message: String) {

        //TODO: remove mock
        val self = ChatMessage(message, UserType.SELF.toInt())
        presenter?.showMessage(self)
        val mockResponse = ChatMessage(message, UserType.OTHER.toInt())
        receivedMessage(mockResponse)
    }

    fun receivedMessage(message: ChatMessage) {

        //TODO: remove mock
        Handler(Looper.getMainLooper()).postDelayed({
            val mockMessage = ChatMessage(message.text, 2)
            presenter?.showMessage(mockMessage)
        }, 1000)
    }
}