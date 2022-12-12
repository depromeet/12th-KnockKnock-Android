package com.depromeet.data.api

import com.depromeet.data.model.request.GetGoogleLoginRequest
import com.depromeet.data.model.request.GetKakaoLoginRequest
import com.depromeet.data.model.request.PostRefreshTokenRequest
import com.depromeet.data.model.response.GetGoogleLoginResponse
import com.depromeet.data.model.response.GetKakaoLoginResponse
import com.depromeet.data.model.response.PostRefreshTokenResponse
import retrofit2.http.GET


interface MainAPIService {
    @GET("/credentials/refresh")
    suspend fun postRefreshToken(body: PostRefreshTokenRequest): PostRefreshTokenResponse

    @GET("/credentials/oauth/kakao")
    suspend fun getKakaoLogin(body: GetKakaoLoginRequest): GetKakaoLoginResponse

    @GET("/credentials/oauth/google")
    suspend fun getGoogleLogin(body: GetGoogleLoginRequest): GetGoogleLoginResponse
}