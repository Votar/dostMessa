package entrego.com.android.socket

import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.neovisionaries.ws.client.*
import entrego.com.android.util.Logger

/**
 * Created by Admin on 13.01.2017.
 */
class SocketClient {
    val END_POINT = "ws://62.149.12.54/mobile-gateway-1.0.0-SNAPSHOT/status"
    val TIMEOUT = 5000 //5sec
    val mSocketConnection: WebSocket
    val mGson = Gson()
    var isNeed: Boolean

    init {
        mSocketConnection = WebSocketFactory().createSocket(END_POINT, TIMEOUT)
        mSocketConnection.addListener(SocketListener())
        isNeed = false
    }

    inner class SocketListener : WebSocketAdapter() {
        val TAG = "SOCKET_RECEIVER"
        val TAG_ERROR = "SOCKET_ERROR"
        val RECONNECT_TIMEOUT = 3000 // sec

        override fun onDisconnected(websocket: WebSocket?, serverCloseFrame: WebSocketFrame?, clientCloseFrame: WebSocketFrame?, closedByServer: Boolean) {
            super.onDisconnected(websocket, serverCloseFrame, clientCloseFrame, closedByServer)
            Logger.logd(TAG, "Try to reconncet socket after")
            if (isNeed)
                websocket
                        ?.recreate(RECONNECT_TIMEOUT)
                        ?.connectAsynchronously()

        }

        override fun onConnected(websocket: WebSocket?, headers: MutableMap<String, MutableList<String>>?) {
            super.onConnected(websocket, headers)
            Logger.logd(TAG, "Socket connected")
        }

        override fun onTextMessage(websocket: WebSocket?, text: String?) {
            super.onTextMessage(websocket, text)
            Logger.logd(TAG, text)
        }

        override fun onConnectError(websocket: WebSocket?, exception: WebSocketException?) {
            super.onConnectError(websocket, exception)
            Logger.loge(TAG_ERROR, exception?.error.toString() ?: "Socket error")
            if (isNeed)
                websocket
                        ?.recreate(RECONNECT_TIMEOUT)
                        ?.connectAsynchronously()


        }

    }

    fun sendText(message: String) {

    }

    fun sendLocation(location: String) {
        Logger.logd(location)
        if (mSocketConnection.isOpen)
            mSocketConnection.sendText(location)
        else {
            if (isNeed)
                openConnection()
        }
    }

    fun openConnection() {
        isNeed = true
        mSocketConnection.recreate()
        mSocketConnection.connectAsynchronously()
    }

    fun closeConnection() {
        isNeed = false
        mSocketConnection.disconnect()
    }


}