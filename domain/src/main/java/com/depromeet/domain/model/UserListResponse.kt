package com.depromeet.domain.model

data class UserListResponse(
    val userList: List<SearchUser>
)

data class SearchUser(
    val id: Int,
    val nickName: String,
    val profilePath: String,
    val isFriend: Boolean
)