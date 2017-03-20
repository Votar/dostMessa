package entrego.com.android.web.model.response.chat

import entrego.com.android.web.model.response.EntregoResult
import entrego.com.android.web.socket.model.ChatSocketMessage


class EntregoGetChatHistoryResponse(code: Int?, message: String?,
                                    val payload: Array<ChatSocketMessage>)
    : EntregoResult(code, message)