package com.depromeet.domain.model

data class RefreshTokenResponse(
    val accessToken: String,
    val refreshToken: String
)