package com.depromeet.domain.model

data class Reservation(
    val reservation_id: Int,
    val title: String,
    val content: String,
    val image_url: String,
    val send_at: String
)