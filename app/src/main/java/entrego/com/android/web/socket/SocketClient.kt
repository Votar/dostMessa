package entrego.com.android.web.socket

import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.neovisionaries.ws.client.*
import entrego.com.android.util.Logger
import java.util.logging.Handler

/**
 * Created by Admin on 13.01.2017.
 */
class SocketClient {

    val END_POINT = "ws://62.149.12.54/mobile-gateway-1.0.0-SNAPSHOT/status"
    val TIMEOUT = 5000 //5sec
    var mSocketConnection: WebSocket? = null
    val mGson = Gson()
    var isNeed: Boolean

    init {
        mSocketConnection = WebSocketFactory().createSocket(END_POINT, TIMEOUT)
                .addListener(SocketListener())
        isNeed = false
    }

    inner class SocketListener : WebSocketAdapter() {
        val TAG = "SOCKET_RECEIVER"
        val TAG_ERROR = "SOCKET_ERROR"
        val RECONNECT_TIMEOUT = 5000 // sec

        override fun onDisconnected(websocket: WebSocket?, serverCloseFrame: WebSocketFrame?, clientCloseFrame: WebSocketFrame?, closedByServer: Boolean) {
            super.onDisconnected(websocket, serverCloseFrame, clientCloseFrame, closedByServer)
            Logger.logd(TAG, "Socked disconnected \n is need - $isNeed")
//            if (isNeed)
//                connectAsync()
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
            when (exception?.error) {
                WebSocketError.NOT_IN_CREATED_STATE ->
                    if (isNeed) {
                        Logger.loge(TAG_ERROR, "Should reconnect after failure connect attemp")
//                        connectAsync()
                    }
                else -> {
                    Logger.loge(TAG_ERROR, exception?.error.toString() ?: "Socket error")
                }
            }
        }
    }

    fun sendLocation(location: String) {
        Logger.logd(location)
        if (mSocketConnection?.isOpen == true) {
            mSocketConnection?.sendText(location)
        } else {
            Logger.logd("SOCKET IS DISCONNECTED")
            openConnection()
        }
    }

    fun openConnection() {
        isNeed = true
        connectAsync()
    }

    private fun connectAsync() {
        mSocketConnection?.disconnect()
        mSocketConnection = mSocketConnection?.recreate(TIMEOUT)
        mSocketConnection = mSocketConnection?.connectAsynchronously()
    }

    fun closeConnection() {
        isNeed = false
        mSocketConnection = mSocketConnection?.disconnect()
    }


}