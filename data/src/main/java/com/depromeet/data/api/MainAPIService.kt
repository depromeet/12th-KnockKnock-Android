package com.depromeet.data.api

import com.depromeet.data.model.request.PostRefreshTokenRequest
import com.depromeet.data.model.request.PostRelationsRequest
import com.depromeet.data.model.request.PutUserNicknameRequest
import com.depromeet.data.model.request.PutUserProfileRequest
import com.depromeet.domain.model.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query


interface MainAPIService {

    // 등록 요청
    @POST("/api/v1/credentials/register")
    suspend fun postRegister(@Query("id_token") idToken: String, @Query("provider") provider: String): LoginResponse

    // 토큰 리프래쉬
    @POST("/api/v1/credentials/refresh")
    suspend fun postRefreshToken(@Body body: PostRefreshTokenRequest): LoginResponse

    // 로그인 요청 <- 가입한 유저
    @POST("/api/v1/credentials/login")
    suspend fun postLogin(@Query("id_token") idToken: String, @Query("provider") provider: String): LoginResponse

    // 토큰 검증
    @GET("/api/v1/credentials/oauth/valid/register")
    suspend fun getTokenValidation(@Query("id_token") idToken: String, @Query("provider") provider: String): isRegistedResponse

    // 유저 프로필
    @GET("/api/v1/users/profile")
    suspend fun getUserProfile(): UserProfile

    // 유저 프로필 변경
    @PUT("/api/v1/users/profile")
    suspend fun putUserProfile(@Body body: PutUserProfileRequest): UserProfile

    // 닉네임 변경
    @PUT("/api/v1/users/profile")
    suspend fun putUserNickname(@Body body: PutUserNicknameRequest): Unit

    // 내 친구 리스트
    @GET("/api/v1/relations")
    suspend fun getRelations(): FriendList

    // 친구요청
    @POST("/api/v1/relations")
    suspend fun postRelations(@Body body: PostRelationsRequest): Unit

    // 유저 닉네임 검색
    @GET("/api/v1/users/nickname/{nickname}")
    suspend fun getUsersNickname(@Path("nickname") nickname: String): UserList

    @GET("/api/v1/groups/{id}")
    suspend fun getGroup(@Path("id") id: Int): Group

}