package com.depromeet.domain.model

data class NotificationListContent(
    val groups: List<Notification>,
    val reservations: List<Reservation>,
    val notifications: NotificationContent,
)