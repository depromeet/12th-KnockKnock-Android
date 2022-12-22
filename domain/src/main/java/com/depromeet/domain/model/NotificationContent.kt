package com.depromeet.domain.model

data class NotificationContent(
    val content: List<Notification>,
    val last: Boolean,
)