package com.depromeet.domain.model

data class UserProfileResponse(
    val id: Int,
    val nickname: String,
    val profilePath: String,
    val email: String,
)