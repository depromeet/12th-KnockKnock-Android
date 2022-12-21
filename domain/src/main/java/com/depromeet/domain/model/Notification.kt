package com.depromeet.domain.model

data class Notification(
    val content: String,
    val created_date: String,
    val image_url: String,
    val notification_id: Int,
    val reactions: Reactions,
    val send_user_id: Int,
    val title: String
)