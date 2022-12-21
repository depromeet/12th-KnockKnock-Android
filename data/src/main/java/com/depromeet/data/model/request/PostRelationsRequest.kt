package com.depromeet.data.model.request

import com.google.gson.annotations.SerializedName


data class PostRelationsRequest(
    @SerializedName("user_id") val user_id: Int
)