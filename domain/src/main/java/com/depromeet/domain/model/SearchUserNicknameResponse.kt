package com.depromeet.domain.model

data class SearchUserNicknameResponse(
    val userList: List<UserList>
)

data class UserList(
    val id: Int,
    val nickName: String,
    val profilePath: String,
    val isFriend: Boolean
)