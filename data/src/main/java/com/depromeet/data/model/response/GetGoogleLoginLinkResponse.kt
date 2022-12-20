package com.depromeet.data.model.response


import com.depromeet.domain.model.Data
import com.depromeet.domain.model.GoogleData
import com.google.gson.annotations.SerializedName

data class GetGoogleLoginLinkResponse(
    @SerializedName("data")
    val data: GoogleData,
    @SerializedName("status")
    val status: Int,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("time_stamp")
    val timeStamp: String
)