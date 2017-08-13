package com.entregoya.msngr.web.new_socket

import okhttp3.Request

internal interface SocketClientContract {

    fun sendMessage(message :String)

    fun reconnect()

    fun closeConnection()

    val isOpen: Boolean

    fun notifySocketState()
}