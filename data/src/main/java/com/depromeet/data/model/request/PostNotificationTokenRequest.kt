package com.depromeet.data.model.request

import com.google.gson.annotations.SerializedName


data class PostNotificationTokenRequest(
    @SerializedName("device_id") val device_id: String,
    @SerializedName("token") val token: String
)