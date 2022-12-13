package com.depromeet.data.model.response

import com.google.gson.annotations.SerializedName

data class GetGoogleLoginResponse(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("refresh_token") val refreshToken: String,
    @SerializedName("is_registered") val isRegistered: Boolean
)