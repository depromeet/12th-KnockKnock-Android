package com.depromeet.data.api

import com.depromeet.data.model.request.*
import com.depromeet.data.model.response.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface MainAPIService {

    // Refresh Token 재발급
    @POST("/credentials/refresh")
    suspend fun postRefreshToken(@Body body: PostRefreshTokenRequest): PostRefreshTokenResponse

    // Oauth Kakao 로그인
    @GET("/credentials/oauth/kakao")
    suspend fun getKakaoLogin(@Body body: GetKakaoLoginRequest): GetKakaoLoginResponse

    // Oauth Google 로그인
    @GET("/credentials/oauth/google")
    suspend fun getGoogleLogin(@Body body: GetGoogleLoginRequest): GetGoogleLoginResponse

    // 유저 닉네임 변경
    @PUT("/users/nickname")
    suspend fun putUserNickname(@Body body: PutUserNicknameRequest): Unit

    // 유저 닉네임 검색
    @GET("/users/nickname")
    suspend fun getUserNickname(@Body body: GetUserNicknameRequest): GetUserNicknameResponse

    // 그룹 정보
    @GET("/groups/{id}")
    suspend fun getGroup(@Path("id") id: Int): GetGroupResponse
    
}