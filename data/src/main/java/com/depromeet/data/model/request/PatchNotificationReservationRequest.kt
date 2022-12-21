package com.depromeet.data.model.request

import com.google.gson.annotations.SerializedName


data class PatchNotificationReservationRequest(
    @SerializedName("reservation_id") val reservation_id: Int,
    @SerializedName("send_at") val send_at: String
)
