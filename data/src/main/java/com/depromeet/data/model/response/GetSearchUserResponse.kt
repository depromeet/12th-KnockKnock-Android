package com.depromeet.data.model.response

import com.depromeet.domain.model.Category
import com.depromeet.domain.model.Friend
import com.depromeet.domain.model.FriendListResponse
import com.depromeet.domain.model.SearchUser
import com.google.gson.annotations.SerializedName

data class GetSearchUserResponse(
    @SerializedName("user_list") val userList: List<SearchUser>,
)