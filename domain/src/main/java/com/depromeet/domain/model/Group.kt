package com.depromeet.domain.model

data class Group(
    val background_image_path: String,
    val category: Category,
    val description: String,
    val group_id: Int,
    val group_type: String,
    val ihost: Boolean,
    val members: List<Member>,
    val public_access: Boolean,
    val thumbnail_path: String,
    val title: String
)