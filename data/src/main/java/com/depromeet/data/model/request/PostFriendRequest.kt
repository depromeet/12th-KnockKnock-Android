package com.depromeet.data.model.request

import com.depromeet.domain.model.Category
import com.google.gson.annotations.SerializedName

data class PostFriendRequest(
    @SerializedName("user_id") val userId: Int
)