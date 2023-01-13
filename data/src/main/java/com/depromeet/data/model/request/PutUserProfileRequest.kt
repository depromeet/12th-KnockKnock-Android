package com.depromeet.data.model.request

import com.google.gson.annotations.SerializedName


data class PutUserProfileRequest(
    @SerializedName("nickname") val nickname: String,
    @SerializedName("profile_path") val profile_path: String
)

//{
//    "nickname": "string",
//    "profile_path": "string"
//}