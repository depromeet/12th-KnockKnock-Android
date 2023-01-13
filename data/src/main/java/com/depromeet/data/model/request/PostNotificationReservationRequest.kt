package com.depromeet.data.model.request

import com.google.gson.annotations.SerializedName


data class PostNotificationReservationRequest(
    @SerializedName("group_id") val group_id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
    @SerializedName("image_url") val image_url: String,
    @SerializedName("send_at") val send_at: String
)
