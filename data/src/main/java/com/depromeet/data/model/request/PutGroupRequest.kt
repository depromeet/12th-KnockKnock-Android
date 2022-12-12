package com.depromeet.data.model.request

import com.google.gson.annotations.SerializedName

data class PutGroupRequest(
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("public_access") val publicAccess: Boolean,
    @SerializedName("thumbnail_path") val thumbnailPath: String,
    @SerializedName("background_image_path") val backgroundImagePath: String,
    @SerializedName("category_id") val categoryId: Int
)