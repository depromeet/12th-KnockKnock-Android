package com.depromeet.domain.model

data class Admission(
    val admission_state: String,
    val created_at: String,
    val id: Int,
    val user: User
)