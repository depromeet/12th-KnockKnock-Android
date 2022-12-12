package com.depromeet.data.mapper

import com.depromeet.data.model.response.GetGoogleLoginResponse
import com.depromeet.data.model.response.GetKakaoLoginResponse
import com.depromeet.data.model.response.PostRefreshTokenResponse
import com.depromeet.domain.model.GoogleLoginResponse
import com.depromeet.domain.model.KakaoLoginResponse
import com.depromeet.domain.model.RefreshTokenResponse


fun PostRefreshTokenResponse.toDomain() : RefreshTokenResponse {
    return RefreshTokenResponse(
        accessToken = this.accessToken,
        refreshToken = this.refreshToken
    )
}

fun GetKakaoLoginResponse.toDomain() : KakaoLoginResponse {
    return KakaoLoginResponse(
        accessToken = this.accessToken,
        refreshToken = this.refreshToken,
        isRegisted = this.isRegistered
    )
}

fun GetGoogleLoginResponse.toDomain() : GoogleLoginResponse {
    return GoogleLoginResponse(
        accessToken = this.accessToken,
        refreshToken = this.refreshToken,
        isRegisted = this.isRegistered
    )
}


