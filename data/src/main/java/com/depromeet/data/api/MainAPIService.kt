package com.depromeet.data.api

import com.depromeet.data.model.request.GetGoogleLoginRequest
import com.depromeet.data.model.request.GetKakaoLoginRequest
import com.depromeet.data.model.request.PostRefreshTokenRequest
import com.depromeet.data.model.response.GetGoogleLoginResponse
import com.depromeet.data.model.response.GetKakaoLoginResponse
import com.depromeet.data.model.response.PostRefreshTokenResponse
import retrofit2.http.GET
import retrofit2.http.POST


interface MainAPIService {

    // Refresh Token 재발급
    @POST("/credentials/refresh")
    suspend fun postRefreshToken(body: PostRefreshTokenRequest): PostRefreshTokenResponse

    // Oauth Kakao 로그인
    @GET("/credentials/oauth/kakao")
    suspend fun getKakaoLogin(body: GetKakaoLoginRequest): GetKakaoLoginResponse

    // Oauth Google 로그인
    @GET("/credentials/oauth/google")
    suspend fun getGoogleLogin(body: GetGoogleLoginRequest): GetGoogleLoginResponse
}