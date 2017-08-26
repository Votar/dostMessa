package com.entregoya.msngr.web.new_socket

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat
import android.support.v4.content.LocalBroadcastManager
import android.util.Log
import com.entregoya.msngr.R
import com.entregoya.msngr.storage.preferences.EntregoStorage
import com.entregoya.msngr.ui.main.RootActivity
import com.entregoya.msngr.ui.main.delivery.description.chat.ChatMessengerActivity
import com.entregoya.msngr.util.GsonHolder
import com.entregoya.msngr.util.Logger
import com.entregoya.msngr.web.api.EntregoApi
import com.entregoya.msngr.web.socket.SocketContract
import com.entregoya.msngr.web.socket.model.BaseSocketMessage
import com.entregoya.msngr.web.socket.model.ChatSocketMessage
import com.entregoya.msngr.web.socket.model.SocketMessageType
import java.util.*


class SocketViewModel : ViewModel() {
    companion object {
        const val TAG = "SocketViewModel"
    }

    private var mKeepAliveTimer: Timer? = null
    private var mContext: Context? = null
    val socketEventStream = MutableLiveData<SocketEvents>()
    val mUserProfile by lazy { EntregoStorage.getUserProfile() }

    fun setContext(appContext: Context) {
        mContext = appContext
    }

    override fun onCleared() {
        super.onCleared()
        stopKeepAliveTimer()
        socketClient.closeConnection()

    }

    private val socketServiceCallback = object : SocketServerCallback {
        override fun closedByServer() {
            socketEventStream.postValue(SocketEvents.CONNECTION_LOST)
            startKeepAliveTimer()
        }

        override fun connectionLost() {
            Log.d(TAG, "Connection Lost")
            socketEventStream.postValue(SocketEvents.CONNECTION_LOST)
            startKeepAliveTimer()
        }

        override fun connectionEstablished() {
            socketEventStream.postValue(SocketEvents.OPEN)
            stopKeepAliveTimer()
        }

        override fun receivedMessage(message: String) {
            parseMessage(message)
        }
    }

    fun parseMessage(json: String) {

        val baseMessage = GsonHolder.instance.fromJson(json, BaseSocketMessage::class.java)
        //I know, but not now
        when (baseMessage.type) {
            SocketMessageType.ORDER_STATUS -> {
                Logger.logd(json)
            }
            SocketMessageType.WAYPOINT -> {
                Logger.logd(json)
            }
            SocketMessageType.ORDER -> Logger.logd(TAG, json)
            SocketMessageType.TRACK -> Logger.logd(TAG, json)
            SocketMessageType.TRACK_LIST -> Logger.logd(TAG, json)
            SocketMessageType.MESSAGE -> {
                Logger.logd(json)
                receivedChatMessage(json)
            }
            else -> IllegalStateException("Invalid type of socket message")
        }
    }

    fun receivedChatMessage(messageJson: String) {
        sendChatMessageEvent(messageJson)
//        GsonHolder
//                .instance
//                .fromJson(messageJson, ChatSocketMessage::class.java)
//                .apply {
//                    if (mUserProfile?.id != sender)
//                        sendChatMessageReceivedNotification(order, subscriber, text)
//                }
    }

    private fun sendChatMessageEvent(messageJson: String) {
        Intent(SocketContract.ReceivedChatMessageBySocketEvent.ACTION).apply {
            putExtra(SocketContract.ReceivedChatMessageBySocketEvent.KEY_MESSAGE, messageJson)
            LocalBroadcastManager.getInstance(mContext).sendBroadcast(this)
        }

    }


    private val socketClient = H2bSocketClient(socketServiceCallback,
            "${EntregoApi.SOCKET_URL}")

    var periodOffset = 5000L

    fun getPeriod(): Long {
        return periodOffset
    }

    private fun startKeepAliveTimer() {
        stopKeepAliveTimer()
        mKeepAliveTimer = Timer()
        mKeepAliveTimer?.schedule(getTimerTask(), getPeriod())
    }

    private fun stopKeepAliveTimer() {
        mKeepAliveTimer?.cancel()
        mKeepAliveTimer?.purge()
        mKeepAliveTimer = null
    }

    private fun getTimerTask(): TimerTask = object : TimerTask() {
        override fun run() {
            Log.d(TAG, "Pre start reconnect")
            socketClient.reconnect()
        }
    }

//    fun sendChatMessageReceivedNotification(orderId: Long, userId: Long, message: String) {
//        mContext?.let {
//            val mBuilder: NotificationCompat.Builder =
//                    NotificationCompat.Builder(mContext)
//                            .setContentTitle(it.getString(R.string.notification_received_chat_message))
//                            .setSmallIcon(R.drawable.map_user_pin)
//                            .setContentText(message)
//
//            val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
//            mBuilder.setSound(alarmSound)
//            mBuilder.setDefaults(Notification.DEFAULT_SOUND or Notification.DEFAULT_LIGHTS or Notification.DEFAULT_VIBRATE)
//            val resultIntent = ChatMessengerActivity.getIntent(it, orderId, userId)
//            val resultPendingIntent =
//                    PendingIntent.getActivity(
//                            mContext,
//                            0,
//                            resultIntent,
//                            PendingIntent.FLAG_UPDATE_CURRENT
//                    )
//            mBuilder.setContentIntent(resultPendingIntent)
//            val mNotifyMgr = it.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            mNotifyMgr.notify(orderId.toInt(), mBuilder.build())
//        }
//    }


}