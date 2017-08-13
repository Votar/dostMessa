package com.entregoya.msngr.web.new_socket


interface SocketServerCallback {
    fun closedByServer()

    fun connectionLost()

    fun connectionEstablished()

    fun receivedMessage(message: String)
}