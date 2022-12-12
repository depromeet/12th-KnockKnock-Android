package com.depromeet.data.model.request

import com.google.gson.annotations.SerializedName

data class PostNotifcationTokenRequest(
    @SerializedName("device_id") val deviceId: String,
    @SerializedName("token") val token: String
)