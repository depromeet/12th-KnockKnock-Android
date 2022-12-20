package com.depromeet.data.model.response

import com.depromeet.domain.model.Category
import com.google.gson.annotations.SerializedName

data class GetUserProfileResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("profile_path") val profilePath: String,
    @SerializedName("email") val email: String,
)