package com.depromeet.domain.model

data class KakaoLoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val isRegisted: Boolean
)