package com.depromeet.knockknock.ui.notification.model


data class Notification(
    val type: NotificationType,
    val notificationId: Int,
    val roomId: Int,
    val userId: Int,
    val userName: String,
    val userImg: String,
    val contents: String,
    val dateTime: String,
)

enum class NotificationType(type: String) {
    NOTIFICATIONALARM("NOTIFICATION_ALARM"),
    INVITEROOM("INVITE_ROOM"),
    INVITEFRIEND("FRIEND_REQUEST")
}