package com.depromeet.domain.repository

import com.depromeet.domain.NetworkResult
import com.depromeet.domain.model.RefreshTokenResponse


interface  MainRepository {

    suspend fun refreshTokenAPI(): NetworkResult<RefreshTokenResponse>
}