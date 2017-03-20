package entrego.com.android.web.socket

interface SocketContract {

    companion object {
        val ACTION_FILTER = "com.entrego.entregouser.web.socket.SOCKET_RECEIVER"
    }

    interface UpdateDeliveryEvent {
        companion object {
            const val ACTION = "com.entrego.entregouser.web.socket.REQUEST_STATUS"
            const val KEY_DELIVERY_ID = "ext_k_del_id"
        }
    }

    interface UpdateOrderEvent {
        companion object {
            const val ACTION = "com.entrego.entregouser.web.socket.REQUEST_DELIVERY"
            const val KEY_DELIVERY_ID = "ext_k_del_id"
        }
    }

    interface SendMessageBySocketEvent {
        companion object {
            const val ACTION = "com.entrego.entregouser.web.socket.SEND_MESSAGE"
            const val KEY_ORDER_ID = "ext_k_order_id"
            const val KEY_MESSAGE = "ext_k_message"
        }
    }
    interface ReceivedChatMessageBySocketEvent {
        companion object {
            const val ACTION = "com.entrego.entregouser.web.socket.RECEIVED_CHAT_MESSAGE"
            const val KEY_ORDER_ID = "ext_k_order_id"
            const val KEY_MESSAGE = "ext_k_message"
        }
    }

    interface UpdateMessengerLocationEvent {
        companion object {
            const val ACTION = "com.entrego.entregouser.web.socket.MESSENGER_LOCATION_UPDATED"
            const val KEY_MESSENGER_LOCATION = "ext_k_messenger_location"
        }
    }

    interface ReceiveMessagesListener {
        fun receivedDeliveryUpdated(deliveryId: Long)
        fun receivedOrderUpdated(deliveryId: Long)
        fun receivedChatMessage(messageJson: String)
    }
}