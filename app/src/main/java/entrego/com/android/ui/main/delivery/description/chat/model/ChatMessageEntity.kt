package entrego.com.android.ui.main.delivery.description.chat.model

import entrego.com.android.ui.main.delivery.description.chat.model.UserType
import java.util.*

data class ChatMessageEntity(val text: String,
                             val userType: UserType,
                             val timestamp: Long = Calendar.getInstance().time.time)