package entrego.com.android.socket

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

/**
 * Created by Admin on 13.01.2017.
 */
class SocketService : Service() {
    companion object {
        val ACTION_FILTER = "entrego.com.android.socket.SOCKET_RECEIVER"
        val KEY_EVENT = "key_event"
        val KEY_MESSAGE = "ext_key_message"
        val KEY_LOCATION = "ext_key_latlng"
    }

    enum class SocketEvent(val value: String) {
        CONNECT("connect"),
        DISCONNECT("disconnect"),
        SEND_TEXT("message")
    }
    var mSocketClient : SocketClient? = null
    
    val mReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.hasExtra(KEY_EVENT) == true)
                when (intent?.getStringExtra(KEY_EVENT)) {
                    SocketEvent.CONNECT.value -> mSocketClient?.openConnection()
                    SocketEvent.DISCONNECT.value -> mSocketClient?.closeConnection()
                    SocketEvent.SEND_TEXT.value -> {
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
        mSocketClient = SocketClient()
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, IntentFilter(ACTION_FILTER))
        mSocketClient?.openConnection()
        Logger.logd("Service open connection")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver)
        mSocketClient?.closeConnection()
        Logger.logd("$SocketService::class.java.simpleName destroyed")
    }

}