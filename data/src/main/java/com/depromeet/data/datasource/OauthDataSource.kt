package com.depromeet.data.datasource

import com.depromeet.data.model.PostRefreshTokenResponse
import com.depromeet.domain.NetworkResult

interface OauthDataSource {
    suspend fun postRefreshToken(): NetworkResult<PostRefreshTokenResponse>
}
