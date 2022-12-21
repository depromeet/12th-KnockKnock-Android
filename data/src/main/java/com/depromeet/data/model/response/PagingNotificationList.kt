package com.depromeet.data.model.response

import com.depromeet.domain.model.Notification
import com.depromeet.domain.model.NotificationContent
import com.depromeet.domain.model.Reservation
import com.google.gson.annotations.SerializedName

data class PagingNotificationList(
    @SerializedName("groups") val groups: List<Notification>,
    @SerializedName("reservations") val reservations: List<Reservation>,
    @SerializedName("notifications") val notifications: NotificationContent,
)