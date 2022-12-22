package com.depromeet.data.model.request

import com.google.gson.annotations.SerializedName


data class PostGroupFriendRequest(
    @SerializedName("member_ids") val member_ids: List<Int>
)