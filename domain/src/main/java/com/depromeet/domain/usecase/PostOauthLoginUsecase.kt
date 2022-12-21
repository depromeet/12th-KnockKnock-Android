package com.depromeet.domain.usecase

import com.depromeet.domain.NetworkResult
import com.depromeet.domain.model.GoogleLoginResponse
import com.depromeet.domain.model.KakaoLoginResponse
import com.depromeet.domain.model.OauthLoginResponse
import com.depromeet.domain.model.RefreshTokenResponse
import com.depromeet.domain.repository.MainRepository
import javax.inject.Inject


class PostOauthLoginUsecase @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(idToken: String, provider: String): NetworkResult<OauthLoginResponse> {
        return repository.postOauthLogin(idToken = idToken, provider = provider)
    }
}