package com.depromeet.domain.model

data class GoogleLoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val isRegisted: Boolean
)