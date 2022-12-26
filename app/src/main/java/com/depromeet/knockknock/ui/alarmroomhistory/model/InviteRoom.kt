package com.depromeet.knockknock.ui.alarmroomhistory.model

data class InviteRoom(
    val notificationId: Int,
    val roomId: Int,
    val userId: Int,
    val userName: String,
    val userImg: String,
    val dateTime: String,
)
