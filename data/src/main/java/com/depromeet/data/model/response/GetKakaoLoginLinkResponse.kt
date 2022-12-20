package com.depromeet.data.model.response


import com.depromeet.domain.model.Data
import com.google.gson.annotations.SerializedName

data class GetKakaoLoginLinkResponse(
    @SerializedName("data")
    val data: Data,
    @SerializedName("status")
    val status: Int,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("time_stamp")
    val timeStamp: String
)