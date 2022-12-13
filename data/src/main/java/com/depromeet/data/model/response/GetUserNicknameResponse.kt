package com.depromeet.data.model.response

import com.depromeet.domain.model.UserList
import com.google.gson.annotations.SerializedName

data class GetUserNicknameResponse(
    @SerializedName("user_list") val userList: List<UserList>
)