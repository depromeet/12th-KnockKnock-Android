package com.depromeet.domain.repository

import com.depromeet.domain.NetworkResult
import com.depromeet.domain.model.GoogleLoginResponse
import com.depromeet.domain.model.KakaoLoginResponse
import com.depromeet.domain.model.RefreshTokenResponse


interface  MainRepository {

    suspend fun refreshTokenAPI(refreshToken: String): NetworkResult<RefreshTokenResponse>

    suspend fun getKakaoLogin(code: String): NetworkResult<KakaoLoginResponse>

    suspend fun getGoogleLogin(code: String): NetworkResult<GoogleLoginResponse>
}