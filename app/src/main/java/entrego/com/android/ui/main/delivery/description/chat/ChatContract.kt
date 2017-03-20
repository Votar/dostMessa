package entrego.com.android.ui.main.delivery.description.chat

import entrego.com.android.mvp.presenter.IBaseMvpPresenter
import entrego.com.android.mvp.view.IBaseMvpView
import entrego.com.android.ui.main.delivery.description.chat.model.ChatMessageEntity
import entrego.com.android.web.socket.model.ChatSocketMessage


object ChatContract {
    interface View : IBaseMvpView {
        fun registerChatReceiver()
        fun unregisterChatReceiver()
        fun popupMessage(message: ChatMessageEntity)
        fun popupMessages(listMessages: List<ChatMessageEntity>)
        fun showProgress()
        fun hideProgress()
    }

    interface Presenter : IBaseMvpPresenter<View> {
        fun setupSenderId(userId: Long)
        fun setupOrderId(orderId: Long)
        fun showMessage(message: ChatSocketMessage)
        fun sendMessage(orderId: Long, text: String)
        fun requestChatHistory()
    }
}