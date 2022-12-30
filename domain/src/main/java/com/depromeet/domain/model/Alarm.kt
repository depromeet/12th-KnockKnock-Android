package com.depromeet.domain.model

data class Alarm (
    val content: String,
    val created_at: String,
    val title: String,
    val type: String,
    val user_id: Int,
    val user_profile: String
)