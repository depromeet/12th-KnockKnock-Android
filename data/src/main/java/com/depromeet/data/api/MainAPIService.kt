package com.depromeet.data.api

import com.depromeet.data.model.PostRefreshTokenResponse
import retrofit2.http.GET


interface MainAPIService {
    @GET("/credentials/refresh")
    suspend fun postRefreshToken(): PostRefreshTokenResponse
}