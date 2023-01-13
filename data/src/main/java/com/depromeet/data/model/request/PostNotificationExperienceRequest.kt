package com.depromeet.data.model.request

import com.google.gson.annotations.SerializedName


data class PostNotificationExperienceRequest(
    @SerializedName("token") val token: String,
    @SerializedName("content") val content: String,
)
