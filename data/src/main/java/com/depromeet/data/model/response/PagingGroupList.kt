package com.depromeet.data.model.response

import com.depromeet.domain.model.GroupContent
import com.google.gson.annotations.SerializedName

data class PagingGroupList(
    @SerializedName("content") val groupContent: List<GroupContent>,
    @SerializedName("last") val last: Boolean
)