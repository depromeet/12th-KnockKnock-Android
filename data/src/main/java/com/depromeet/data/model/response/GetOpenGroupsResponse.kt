package com.depromeet.data.model.response

import com.depromeet.domain.model.Group
import com.google.gson.annotations.SerializedName

data class GetOpenGroupsResponse(
    @SerializedName("group_infos") val groupInfos: List<Group>
)