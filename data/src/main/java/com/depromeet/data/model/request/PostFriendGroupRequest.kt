package com.depromeet.data.model.request

import com.google.gson.annotations.SerializedName

data class PostFriendGroupRequest(
    @SerializedName("member_ids") val memberIds: List<Int>
)