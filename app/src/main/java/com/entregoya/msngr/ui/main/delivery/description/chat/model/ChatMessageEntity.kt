package com.entregoya.msngr.ui.main.delivery.description.chat.model

import com.entregoya.msngr.ui.main.delivery.description.chat.model.UserType
import java.util.*

data class ChatMessageEntity(val text: String,
                             val userType: UserType,
                             val timestamp: Long = Calendar.getInstance().time.time)