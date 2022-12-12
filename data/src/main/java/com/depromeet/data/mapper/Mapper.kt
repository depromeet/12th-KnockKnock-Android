package com.depromeet.data.mapper

import com.depromeet.data.model.PostRefreshTokenResponse
import com.depromeet.domain.model.RefreshTokenResponse


fun PostRefreshTokenResponse.toDomain() : RefreshTokenResponse {
    return RefreshTokenResponse(
        accessToken = this.accessToken,
        refreshToken = this.refreshToken
    )
}

