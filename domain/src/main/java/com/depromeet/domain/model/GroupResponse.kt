package com.depromeet.domain.model

data class GroupResponse(
    val groupId: Int,
    val title: String,
    val description: String,
    val thumbnailPath: String,
    val backgroundImagePath: String,
    val publicAccess: Boolean,
    val category: Category,
    val members: List<Member>,
    val groupType: String,
    val host: Boolean,
)

data class Category(
    val id: Int,
    val emoji: String,
    val content: String,
)

data class Member(
    val userId: Int,
    val nickName: String,
    val profilePath: String
)