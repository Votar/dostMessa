package entrego.com.android.web.socket

interface SocketContract {


    interface ReceiveMessagesListener {
        fun receivedMessage(message: String, event: SocketConnectionEvents)
    }
}