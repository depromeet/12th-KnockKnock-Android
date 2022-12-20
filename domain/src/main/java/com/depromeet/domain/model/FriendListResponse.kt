package com.depromeet.domain.model

data class FriendListResponse(
    val friendList: List<Friend>
)

data class Friend(
    val id: Int,
    val nickName: String,
    val profilePath: String
)