package com.depromeet.domain.model

data class KakaoLoginLinkResponse (
    val data: Data,
    val status: Int,
    val success: Boolean,
    val timeStamp: String
)


data class Data(
    val link: String
)