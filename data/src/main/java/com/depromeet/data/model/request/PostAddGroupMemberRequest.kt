package com.depromeet.data.model.request

import com.google.gson.annotations.SerializedName

data class PostAddGroupMemberRequest(
    @SerializedName("member_ids") val memberIds: List<Int>
)