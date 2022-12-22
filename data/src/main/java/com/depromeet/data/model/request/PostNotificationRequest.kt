package com.depromeet.data.model.request

import com.google.gson.annotations.SerializedName


data class PostNotificationRequest(
    @SerializedName("group_id") val group_id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
    @SerializedName("image_url") val image_url: String
)

//{
//    "group_id": 0,
//    "title": "string",
//    "content": "string",
//    "image_url": "string"
//}