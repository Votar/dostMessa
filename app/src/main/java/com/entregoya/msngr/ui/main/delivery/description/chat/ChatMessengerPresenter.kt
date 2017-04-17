package com.entregoya.msngr.ui.main.delivery.description.chat

import com.entregoya.msngr.mvp.presenter.BaseMvpPresenter
import com.entregoya.msngr.storage.preferences.EntregoStorage
import com.entregoya.msngr.ui.main.delivery.description.chat.model.ChatMessageEntity
import com.entregoya.msngr.ui.main.delivery.description.chat.model.GetChatHistoryRequest
import com.entregoya.msngr.ui.main.delivery.description.chat.model.SendChatMessageRequest
import com.entregoya.msngr.ui.main.delivery.description.chat.model.UserType
import com.entregoya.msngr.web.api.ApiContract
import com.entregoya.msngr.web.api.ApiCreator
import com.entregoya.msngr.web.model.request.chat.ChatMessageBody
import com.entregoya.msngr.web.socket.model.ChatSocketMessage

class ChatMessengerPresenter : BaseMvpPresenter<ChatContract.View>(),
        ChatContract.Presenter {


    val mToken = EntregoStorage.getToken()
    var mOrderId: Long = 0
    var mSenderId: Long = 0

    val mSendMessageResponseListener = object : SendChatMessageRequest.ResponseListener {
        override fun onSuccessResponse() {

        }

        override fun onFailureResponse(code: Int?, message: String?) {
            mView?.showError(message)
            when (code) {
                ApiContract.RESPONSE_INVALID_TOKEN -> mView?.onLogout()
                else -> mView?.showError(message)
            }
        }
    }

    override fun showMessage(message: ChatSocketMessage) {
        val chatMessage: ChatMessageEntity

        if (message.order != mOrderId) return

        if (message.sender == mSenderId)
            chatMessage = ChatMessageEntity(
                    message.text,
                    UserType.SELF,
                    message.timestamp)
        else
            chatMessage = ChatMessageEntity(
                    message.text,
                    UserType.OTHER,
                    message.timestamp)

        mView?.popupMessage(chatMessage)
    }

    override fun setupSenderId(userId: Long) {
        mSenderId = userId
    }

    override fun sendMessage(orderId: Long, text: String) {
        val message = ChatMessageBody(orderId, text)
        SendChatMessageRequest().requestAsync(mToken, message, mSendMessageResponseListener)
    }

    val mGetHistoryResponseListener = object : GetChatHistoryRequest.ResponseListener {
        override fun onFailureResponse(code: Int?, message: String?) {
            mView?.hideProgress()
            when (code) {
                ApiContract.RESPONSE_INVALID_TOKEN -> mView?.onLogout()
                else -> mView?.showError(message)
            }
        }

        override fun onSuccessResponse(reslutList: List<ChatSocketMessage>) {
            mView?.hideProgress()
            val convertedList = reslutList.map {
                val chatMessage: ChatMessageEntity
                if (it.sender == mSenderId)
                    chatMessage = ChatMessageEntity(
                            it.text,
                            UserType.SELF,
                            it.timestamp)
                else
                    chatMessage = ChatMessageEntity(
                            it.text,
                            UserType.OTHER,
                            it.timestamp)

                return@map chatMessage
            }

            mView?.popupMessages(convertedList)
        }

    }

    override fun setupOrderId(orderId: Long) {
        mOrderId = orderId
    }

    override fun requestChatHistory() {
        mView?.showProgress()
        GetChatHistoryRequest().requestAsync(mToken, mOrderId, mGetHistoryResponseListener)
    }
}