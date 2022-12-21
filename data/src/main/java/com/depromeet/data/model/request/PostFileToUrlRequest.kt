package com.depromeet.data.model.request

import com.google.gson.annotations.SerializedName


data class PostFileToUrlRequest(
    @SerializedName("file") val file: String
)