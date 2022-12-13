package com.depromeet.data.model.request

import com.google.gson.annotations.SerializedName

data class PostRefreshTokenRequest(
    @SerializedName("refresh_token") val refreshToken: String
)