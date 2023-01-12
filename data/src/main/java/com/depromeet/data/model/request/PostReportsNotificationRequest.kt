package com.depromeet.data.model.request

import com.google.gson.annotations.SerializedName


data class PostReportsNotificationRequest(
    @SerializedName("description") val description: String,
    @SerializedName("report_reason") val report_reason: String
)