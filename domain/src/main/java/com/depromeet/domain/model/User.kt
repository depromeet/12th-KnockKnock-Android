package com.depromeet.domain.model

data class User(
    val friend: Boolean,
    val id: Int,
    val nickname: String,
    val profile_path: String
)