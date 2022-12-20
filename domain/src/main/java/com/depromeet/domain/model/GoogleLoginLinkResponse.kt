package com.depromeet.domain.model

data class GoogleLoginLinkResponse (
    val data: GoogleData,
    val status: Int,
    val success: Boolean,
    val timeStamp: String
)


data class GoogleData(
    val link: String
)