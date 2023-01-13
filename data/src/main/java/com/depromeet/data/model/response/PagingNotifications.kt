package com.depromeet.data.model.response

import com.depromeet.domain.model.Notification
import com.google.gson.annotations.SerializedName

data class PagingNotifications(
    @SerializedName("contnet") val content: List<Notification>,
    @SerializedName("last") val last: Boolean,
)