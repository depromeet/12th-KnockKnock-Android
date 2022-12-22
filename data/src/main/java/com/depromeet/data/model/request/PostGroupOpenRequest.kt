package com.depromeet.data.model.request

import com.google.gson.annotations.SerializedName


data class PostGroupOpenRequest(
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("public_access") val public_access: Boolean,
    @SerializedName("thumbnail_path") val thumbnail_path: String,
    @SerializedName("background_image_path") val background_image_path: String,
    @SerializedName("category_id") val category_id: Int,
    @SerializedName("member_ids") val member_ids: List<Int>
)