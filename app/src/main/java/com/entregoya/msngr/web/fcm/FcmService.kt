package com.entregoya.msngr.web.fcm

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.entregoya.msngr.R
import com.entregoya.msngr.storage.preferences.EntregoStorage
import com.entregoya.msngr.ui.main.delivery.description.chat.ChatMessengerActivity
import com.entregoya.msngr.util.GsonHolder
import com.entregoya.msngr.util.Logger
import com.entregoya.msngr.web.new_socket.SocketViewModel
import com.entregoya.msngr.web.socket.model.BaseSocketMessage
import com.entregoya.msngr.web.socket.model.ChatSocketMessage
import com.entregoya.msngr.web.socket.model.SocketMessageType
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class FcmService : FirebaseMessagingService() {

    companion object {
        const val TAG = "FcmService"
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]
        val token = EntregoStorage.getToken()
        if (token.isNullOrEmpty()) return
        // Check if message contains a data payload.
        if (remoteMessage.data != null && remoteMessage.data.size > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.data)
            parseMessage(remoteMessage.data.get("json"))

        }

        // Check if message contains a notification payload.

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    fun parseMessage(json: String?) {

        val baseMessage = GsonHolder.instance.fromJson(json, BaseSocketMessage::class.java)
        //I know, but not now
        when (baseMessage?.type) {
            SocketMessageType.ORDER_STATUS -> {
                Log.d(TAG, json)
            }
            SocketMessageType.WAYPOINT -> {
                Log.d(TAG, json)
            }
            SocketMessageType.ORDER -> Log.d(TAG, json)
            SocketMessageType.TRACK -> Log.d(TAG, json)
            SocketMessageType.TRACK_LIST -> Log.d(TAG, json)
            SocketMessageType.MESSAGE -> {
                Log.d(TAG, json)
                receivedChatMessage(json!!)
            }
            else -> Log.e(TAG, "Invalid type of push message $json")
        }
    }

    private fun receivedChatMessage(json: String) {
        val profile = EntregoStorage.getUserProfile()
        GsonHolder
                .instance
                .fromJson(json, ChatSocketMessage::class.java)
                .apply {
                    if (profile?.id != sender)
                        sendChatMessageReceivedNotification(order, subscriber, text)
                }
    }

    fun sendOrderReceivedNotification(orderId: Long, userId: Long, message: String) {

        val resultIntent = ChatMessengerActivity.getIntent(applicationContext, orderId, userId)
        val resultPendingIntent =
                PendingIntent.getActivity(
                        applicationContext,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                )

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.map_user_pin)
                .setContentTitle(getString(R.string.notification_received_chat_message))
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(resultPendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }

    fun sendChatMessageReceivedNotification(orderId: Long, userId: Long, message: String) {

        val resultIntent = ChatMessengerActivity.getIntent(applicationContext, orderId, userId)
        val resultPendingIntent =
                PendingIntent.getActivity(
                        applicationContext,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                )

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.map_user_pin)
                .setContentTitle(getString(R.string.notification_received_chat_message))
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(resultPendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }

}