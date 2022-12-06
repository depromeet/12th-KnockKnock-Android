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

enum class NotificationType(type: Int) {
    NOTIFICATIONALARM(0),
    INVITEROOM(1),
    INVITEFRIEND(2)
}