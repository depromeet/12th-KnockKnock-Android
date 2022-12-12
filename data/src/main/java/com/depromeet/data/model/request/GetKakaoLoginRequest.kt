package com.depromeet.data.model.request

import com.google.gson.annotations.SerializedName

data class GetKakaoLoginRequest(
    @SerializedName("code") val code: String
)