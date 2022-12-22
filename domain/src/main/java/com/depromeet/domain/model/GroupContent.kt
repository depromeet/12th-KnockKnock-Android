package com.depromeet.domain.model

data class GroupContent(
    val category: Category,
    val description: String,
    val group_id: Int,
    val group_type: String,
    val member_count: Int,
    val public_access: Boolean,
    val thumbnail_path: String,
    val title: String
)