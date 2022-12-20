package com.depromeet.domain.model

import com.sun.org.apache.xpath.internal.operations.Bool

data class UserListResponse(
    val userList: List<SearchUser>
)

data class SearchUser(
    val id: Int,
    val nickName: String,
    val profilePath: String,
    val isFriend: Bool
)