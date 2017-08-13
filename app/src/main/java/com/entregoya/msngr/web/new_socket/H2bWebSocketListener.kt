package com.entregoya.msngr.web.new_socket

import android.util.Log
import okhttp3.Response
import okhttp3.WebSocket
import okio.ByteString
import java.io.EOFException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


class H2bWebSocketListener(val mServerCallback: SocketServerCallback) : okhttp3.WebSocketListener() {
    private val TAG = H2bWebSocketListener::class.java.simpleName
    var isAlive: Boolean = false
        private set

    override fun onOpen(webSocket: WebSocket?, response: Response?) {
        super.onOpen(webSocket, response)
        isAlive = true
        Log.d(TAG, "onOpen")
        mServerCallback.connectionEstablished()
    }

    override fun onMessage(webSocket: WebSocket?, text: String) {
        super.onMessage(webSocket, text)
        mServerCallback.receivedMessage(text)
        Log.d(TAG, "${text} received message")
    }

    override fun onMessage(webSocket: WebSocket?, bytes: ByteString?) {
        super.onMessage(webSocket, bytes)
    }

    override fun onClosing(webSocket: WebSocket?, code: Int, reason: String?) {
        super.onClosing(webSocket, code, reason)
        isAlive = false
        Log.d(TAG, "onClosing code= $code reason= $reason")
        webSocket?.close(code, reason)
    }

    override fun onClosed(webSocket: WebSocket?, code: Int, reason: String?) {
        super.onClosed(webSocket, code, reason)
        isAlive = false
        if(code != 1000)
        mServerCallback.connectionLost()
        Log.d(TAG, "onClosed code= $code reason= $reason")
    }

    override fun onFailure(webSocket: WebSocket?, t: Throwable?, response: Response?) {
        super.onFailure(webSocket, t, response)
        //Exception mean that connection with an end point have been lost
        if (t is EOFException) {
            isAlive = false
            mServerCallback.connectionLost()
            //Could not connect to server after timeout
        } else if (t is SocketTimeoutException) {
            isAlive = false
            mServerCallback.connectionLost()
            // Could not close closed connection
        } else if (t is UnknownHostException) {
            isAlive = false
            mServerCallback.connectionLost()
        } else if (t is IOException) {
            isAlive = false
            mServerCallback.connectionLost()
        } else
            t!!.printStackTrace()

        Log.e(TAG, "Socket failure: ${t.toString()}")
    }

    fun notifySocketState() {
        if (isAlive)
            mServerCallback.connectionEstablished()
        else
            mServerCallback.connectionLost()
    }
}