package com.depromeet.domain.usecase

import com.depromeet.domain.NetworkResult
import com.depromeet.domain.model.RefreshTokenResponse
import com.depromeet.domain.repository.MainRepository
import javax.inject.Inject


class PostRefreshTokenUsecase @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(refreshToken: String): NetworkResult<RefreshTokenResponse> {
        return repository.postRefreshToken(refreshToken = refreshToken)
    }
}