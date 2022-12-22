package com.depromeet.domain.model

data class UserProfile(
    val email: String,
    val id: Int,
    val nickname: String,
    val profile_path: String
)