package com.depromeet.data.model.request

import com.google.gson.annotations.SerializedName

data class PutUserNicknameRequest(
    @SerializedName("nickname") val nickName: String
)