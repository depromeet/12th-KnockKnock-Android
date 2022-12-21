package com.depromeet.domain.model

data class OauthLoginResponse(
    val accessToken: String,
    val refreshToken: String
)