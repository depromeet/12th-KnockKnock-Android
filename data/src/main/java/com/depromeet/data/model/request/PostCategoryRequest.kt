package com.depromeet.data.model.request

import com.google.gson.annotations.SerializedName

data class PostCategoryRequest(
    @SerializedName("emoji") val emoji: String,
    @SerializedName("content") val content: String
)