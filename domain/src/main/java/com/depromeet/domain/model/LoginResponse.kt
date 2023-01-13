package com.depromeet.domain.model

data class LoginResponse(
    val access_token: String,
    val refresh_token: String
)