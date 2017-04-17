package com.entregoya.msngr.web.model.response.chat

import com.entregoya.msngr.web.model.response.EntregoResult
import com.entregoya.msngr.web.socket.model.ChatSocketMessage


class EntregoGetChatHistoryResponse(code: Int?, message: String?,
                                    val payload: Array<ChatSocketMessage>)
    : EntregoResult(code, message)