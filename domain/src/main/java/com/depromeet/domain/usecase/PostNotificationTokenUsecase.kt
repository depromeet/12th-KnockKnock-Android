package com.depromeet.domain.usecase

import com.depromeet.domain.NetworkResult
import com.depromeet.domain.model.RefreshTokenResponse
import com.depromeet.domain.repository.MainRepository
import javax.inject.Inject


class PostNotificationTokenUsecase @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(deviceId: String, token: String): NetworkResult<Unit> {
        return repository.postNotificationToken(deviceId = deviceId, token = token)
    }
}