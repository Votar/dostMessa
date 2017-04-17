package com.entregoya.msngr.ui.main.delivery.description.chat

import com.entregoya.msngr.mvp.presenter.IBaseMvpPresenter
import com.entregoya.msngr.mvp.view.IBaseMvpView
import com.entregoya.msngr.ui.main.delivery.description.chat.model.ChatMessageEntity
import com.entregoya.msngr.web.socket.model.ChatSocketMessage


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