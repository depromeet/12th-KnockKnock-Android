package com.depromeet.knockknock.util

import android.R
import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.depromeet.knockknock.ui.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.util.*


class KnockKnockFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        //token을 서버로 전송
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        val notificationManager = NotificationManagerCompat.from(
            applicationContext
        )
        var builder: NotificationCompat.Builder? = null
        builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (notificationManager.getNotificationChannel("KnockKnock") == null) {
                val channel = NotificationChannel(
                    "KnockKnock",
                    "KnockKnock",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                notificationManager.createNotificationChannel(channel)
            }
            NotificationCompat.Builder(applicationContext, "KnockKnock")
        } else {
            NotificationCompat.Builder(applicationContext)
        }
        val title = remoteMessage.notification!!.title
        val body = remoteMessage.notification!!.body
        builder.setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.drawable.ic_delete)
        val notification: Notification = builder.build()
        notificationManager.notify(1, notification)

//        remoteMessage.notification?.let {
//            showNotification(it)
//        }
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    @RequiresApi(Build.VERSION_CODES.M)
    private fun showNotification(notification: RemoteMessage.Notification) {
        val intent = Intent(this, MainActivity::class.java)
        val pIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val channelId = "KnockKnock"

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSmallIcon(R.drawable.ic_delete)
            .setContentTitle(notification.title)
            .setContentText(notification.body)
            .setContentIntent(pIntent)

        getSystemService(NotificationManager::class.java).run {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(channelId, "알림", NotificationManager.IMPORTANCE_HIGH)
                createNotificationChannel(channel)
            }

            notify(Date().time.toInt(), notificationBuilder.build())
        }
    }
}