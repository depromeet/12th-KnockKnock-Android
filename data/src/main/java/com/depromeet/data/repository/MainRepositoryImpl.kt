package com.depromeet.data.repository

import com.depromeet.data.api.MainAPIService
import com.depromeet.data.api.handleApi
import com.depromeet.data.mapper.toDomain
import com.depromeet.data.model.request.GetGoogleLoginRequest
import com.depromeet.data.model.request.GetKakaoLoginRequest
import com.depromeet.data.model.request.PostRefreshTokenRequest
import com.depromeet.domain.NetworkResult
import com.depromeet.domain.model.GoogleLoginResponse
import com.depromeet.domain.model.KakaoLoginResponse
import com.depromeet.domain.model.RefreshTokenResponse
import com.depromeet.domain.repository.MainRepository
import javax.inject.Inject
import javax.inject.Named

class MainRepositoryImpl @Inject constructor(
    @Named("Main") private val mainAPIService: MainAPIService
): MainRepository {

    override suspend fun refreshTokenAPI(refreshToken: String): NetworkResult<RefreshTokenResponse> {
        val body = PostRefreshTokenRequest(refreshToken = refreshToken)
        return handleApi { mainAPIService.postRefreshToken(body = body).toDomain() }
    }

    override suspend fun getKakaoLogin(code: String): NetworkResult<KakaoLoginResponse> {
        val body = GetKakaoLoginRequest(code = code)
        return handleApi { mainAPIService.getKakaoLogin(body = body).toDomain() }
    }

    override suspend fun getGoogleLogin(code: String): NetworkResult<GoogleLoginResponse> {
        val body = GetGoogleLoginRequest(code = code)
        return handleApi { mainAPIService.getGoogleLogin(body = body).toDomain() }
    }
}