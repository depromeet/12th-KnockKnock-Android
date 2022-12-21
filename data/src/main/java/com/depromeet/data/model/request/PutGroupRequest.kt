package com.depromeet.data.model.request

import com.google.gson.annotations.SerializedName


data class PutGroupRequest(
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("public_access") val public_access: Boolean,
    @SerializedName("thumbnail_path") val thumbnail_path: String,
    @SerializedName("background_image_path") val background_image_path: String,
    @SerializedName("category_id") val category_id: Int,
)
//{
//    "title": "string",
//    "description": "string",
//    "public_access": true,
//    "thumbnail_path": "string",
//    "background_image_path": "string",
//    "category_id": 1
//}