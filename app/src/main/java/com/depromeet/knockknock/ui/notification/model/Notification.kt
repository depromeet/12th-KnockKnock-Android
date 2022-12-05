package com.depromeet.knockknock.ui.notification.model

interface Notification

data class NotificationAlarm(
    val notificationId: Int,
    val userName: String,
    val userImg: String,
    val contents: String,
    val dateTime: String,
): Notification

data class InviteRoom(
    val roomId: Int,
    val userName: String,
    val userImg: String,
    val dateTime: String
): Notification

data class InviteFriend(
    val userId: Int,
    val userName: String,
    val userImg: String,
    val dateTime: String
): Notification