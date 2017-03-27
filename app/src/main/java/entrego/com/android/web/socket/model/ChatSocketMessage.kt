package entrego.com.android.web.socket.model

class ChatSocketMessage(val order: Long,
                        val text: String,
                        val timestamp: Long,
                        val subscriber:Long,
                        val sender: Long) {
    override fun toString(): String {
        return "ChatSocketMessage(order=$order, text='$text', sender=$sender)"
    }
}