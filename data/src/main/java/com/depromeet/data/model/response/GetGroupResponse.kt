package com.depromeet.data.model.response

import com.depromeet.domain.model.Category
import com.depromeet.domain.model.Member
import com.google.gson.annotations.SerializedName

data class GetGroupResponse(
    @SerializedName("group_id") val groupId: Int,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("thumbnail_path") val thumbnailPath: String,
    @SerializedName("background_image_path") val backgroundImagePath: String,
    @SerializedName("public_access") val publicAccess: Boolean,
    @SerializedName("category") val category: Category,
    @SerializedName("members") val members: List<Member>,
    @SerializedName("group_type") val groupType: String,
    @SerializedName("ihost") val host: Boolean,
)