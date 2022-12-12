package com.depromeet.data.repository

import com.depromeet.data.api.MainAPIService
import com.depromeet.data.api.handleApi
import com.depromeet.data.mapper.toDomain
import com.depromeet.data.model.PostRefreshTokenResponse
import com.depromeet.domain.NetworkResult
import com.depromeet.domain.model.RefreshTokenResponse
import com.depromeet.domain.repository.MainRepository
import javax.inject.Inject
import javax.inject.Named

class MainRepositoryImpl @Inject constructor(
    @Named("Main") private val mainAPIService: MainAPIService
): MainRepository {

    override suspend fun refreshTokenAPI(): NetworkResult<RefreshTokenResponse> {
        return handleApi { mainAPIService.postRefreshToken().toDomain() }
    }
}