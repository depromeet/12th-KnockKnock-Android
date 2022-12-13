package com.depromeet.domain.usecase

import com.depromeet.domain.NetworkResult
import com.depromeet.domain.model.GoogleLoginResponse
import com.depromeet.domain.model.KakaoLoginResponse
import com.depromeet.domain.model.RefreshTokenResponse
import com.depromeet.domain.repository.MainRepository
import javax.inject.Inject


class GetGoogleLoginUsecase @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(code: String): NetworkResult<GoogleLoginResponse> {
        return repository.getGoogleLogin(code = code)
    }
}