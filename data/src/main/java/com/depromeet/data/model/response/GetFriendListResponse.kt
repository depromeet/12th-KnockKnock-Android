package com.depromeet.data.model.response

import com.depromeet.domain.model.Category
import com.depromeet.domain.model.Friend
import com.depromeet.domain.model.FriendListResponse
import com.google.gson.annotations.SerializedName

data class GetFriendListResponse(
    @SerializedName("friend_list") val friendList: List<Friend>,
)