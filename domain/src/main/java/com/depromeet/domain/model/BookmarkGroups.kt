package com.depromeet.domain.model

data class BookmarkGroups(
    val group_id: Int,
    val title: String,
    val description: String,
    val thumbnail_path: String,
    val public_access: Boolean,
    val group_type: String
)