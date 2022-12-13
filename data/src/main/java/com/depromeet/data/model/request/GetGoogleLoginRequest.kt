package com.depromeet.data.model.request

import com.google.gson.annotations.SerializedName

data class GetGoogleLoginRequest(
    @SerializedName("code") val code: String
)