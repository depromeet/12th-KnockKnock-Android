package com.depromeet.data.model

import com.google.gson.annotations.SerializedName

data class PostRefreshTokenResponse(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("refreshToken") val refreshToken: String
)