package com.entregoya.msngr.web.new_socket

import android.util.Log
import com.entregoya.msngr.EntregoApplication
import com.entregoya.msngr.storage.preferences.EntregoStorage
import com.entregoya.msngr.web.api.EntregoApi
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocketListener
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit


class H2bSocketClient(listener: SocketServerCallback, val url: String) : SocketClientContract {

    private val READ_TIMEOUT = 60L // sec
    private val WRITE_TIMEOUT = 60L //sec
    private val CONNECT_TIMEOUT = 3L //sec

    private var mSocketConnection: okhttp3.WebSocket
    private val mOkHttpClient: OkHttpClient

    private var mRequest: Request = Request.Builder()
            .addHeader(EntregoApi.TOKEN, EntregoStorage.getToken())
            .url(url)
            .build()

    private val TAG = H2bSocketClient::class.java.simpleName
    private var socketCallback: SocketServerCallback = listener
    private var mWebSocketListener = H2bWebSocketListener(listener)

    init {
        val builder = OkHttpClient().newBuilder()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        builder.addInterceptor(interceptor)
        builder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        builder.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        builder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        mOkHttpClient = builder.build()
        mSocketConnection = createConnection(mRequest, mWebSocketListener)
    }

    override fun reconnect() {
        Log.d(TAG, "Try to reconnect")
        if (mWebSocketListener.isAlive.not())
            mSocketConnection = createConnection(mRequest, mWebSocketListener)
        else
            Log.d(TAG, "WebSocketConnection already created")
    }

    override fun sendMessage(message: String) {
        if (mWebSocketListener.isAlive)
            mSocketConnection.send(message)
    }

    override fun closeConnection() {
        mSocketConnection.close(1000, "Close by user")
    }

    override val isOpen: Boolean = mWebSocketListener.isAlive


    override fun notifySocketState() {
        mWebSocketListener.notifySocketState()
    }


    private fun createConnection(request: Request, listener: WebSocketListener): okhttp3.WebSocket {
        mSocketConnection?.close(1000, "Need other instance")
        return mOkHttpClient.newWebSocket(request, listener)
    }

}