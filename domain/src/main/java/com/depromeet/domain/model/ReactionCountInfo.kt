package com.depromeet.domain.model

data class ReactionCountInfo(
    val notification_id: Int,
    val reaction_count: String,
    val reaction_url: String,
    val reaction_id: Int
)