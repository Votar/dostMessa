package entrego.com.android.web.socket

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.support.v4.content.LocalBroadcastManager
import android.text.TextUtils
import com.google.gson.Gson
import com.neovisionaries.ws.client.WebSocket
import com.neovisionaries.ws.client.WebSocketAdapter
import com.neovisionaries.ws.client.WebSocketException
import com.neovisionaries.ws.client.WebSocketFrame
import entrego.com.android.util.Logger
import java.net.Socket

class SocketService : Service() {
    companion object {
        val ACTION_FILTER = "entrego.com.android.web.socket.SOCKET_RECEIVER"
        val KEY_EVENT = "key_event"
        val KEY_MESSAGE = "ext_key_message"
        val KEY_LOCATION = "ext_key_latlng"
        val RECEIVED_KEY = "service_ext_key"

    }

    enum class SocketServiceEvents(val value: String) {
        CONNECT("connect"),
        DISCONNECT("disconnect"),
        SEND_TEXT("message")
    }

    var mSocketClient: SocketClient? = null
    var mReceiveMessagesListener = object : SocketContract.ReceiveMessagesListener {
        override fun receivedMessage(message: String, event: SocketConnectionEvents) {
            applicationContext?.apply {
                val brManager = LocalBroadcastManager.getInstance(this)
                val intent = Intent(event.value)
                intent.putExtra(RECEIVED_KEY, message)
                brManager.sendBroadcast(intent)
            }
        }
    }
    val mReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.hasExtra(KEY_EVENT) == true)
                when (intent?.getStringExtra(KEY_EVENT)) {
                    SocketServiceEvents.CONNECT.value -> mSocketClient?.openConnection()
                    SocketServiceEvents.DISCONNECT.value -> mSocketClient?.closeConnection()
                    SocketServiceEvents.SEND_TEXT.value -> {
                        Logger.logd("Received event in service")
                        val jsonLocation = intent?.getStringExtra(KEY_LOCATION) ?: "MOCKED_LOCATION"
                        mSocketClient?.sendLocation(jsonLocation)
                    }
                }
        }

    }

    override fun onBind(intent: Intent?): IBinder {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate() {
        super.onCreate()
        mSocketClient = SocketClient(mReceiveMessagesListener)
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, IntentFilter(ACTION_FILTER))
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver)
        mSocketClient?.closeConnection()
        Logger.logd("SocketService destroyed")
    }


}