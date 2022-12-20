package com.depromeet.data.model.request

import com.depromeet.domain.model.Category
import com.google.gson.annotations.SerializedName

data class PutUserProfileRequest(
    @SerializedName("nickname") val nickname: String,
    @SerializedName("profile_path") val profilePath: String
)