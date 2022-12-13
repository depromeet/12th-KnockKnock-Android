package com.depromeet.domain.model

data class OpenGroupsResponse(
    val groupInfos: List<Group>
)

data class Group(
    val groupId: Int,
    val title: String,
    val description: String,
    val thumbnailPath: String,
    val publicAccess: Boolean,
    val category: Category,
    val memberCount: Int,
    val groupType: String
)