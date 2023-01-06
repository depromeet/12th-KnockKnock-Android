package com.depromeet.data.interceptor

import com.depromeet.data.DataApplication.Companion.editor
import com.depromeet.data.DataApplication.Companion.sSharedPreferences
import com.depromeet.data.api.ApiClient.BASE_URL
import com.depromeet.data.api.MainAPIService
import com.depromeet.data.api.handleApi
import com.depromeet.data.model.request.PostRefreshTokenRequest
import com.depromeet.domain.onError
import com.depromeet.domain.onSuccess
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

/*
   * bearer 토큰 필요한 api 사용시 accessToken유효한지 검사
   * 유효하지 않다면 재발급 api 호출
   * refreshToken이 유효하다면 정상적으로 accessToken재발급 후 기존 api 동작 완료
   * 사용시 주석 풀고 사용하기
*/

class BearerInterceptor : Interceptor {
    //todo 조건 분기로 인터셉터 구조 변경
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        val errorResponse = response.body?.string()?.let { createErrorResponse(it) }

        var accessToken = ""
        var isRefreshable = false
        if(errorResponse?.status == 401) {
            runBlocking {
                //토큰 갱신 api 호출
                sSharedPreferences.getString("refresh_token", null)?.let {
                    val request = PostRefreshTokenRequest(it)
                    val result = handleApi {
                        Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                            .create(MainAPIService::class.java).postRefreshToken(request)
                    }

                    result.onSuccess { response ->
                        editor.putString("access_token", response.data.access_token)
                        editor.putString("refresh_token", response.data.refresh_token)
                        editor.commit()
                        accessToken = response.data.access_token
                        isRefreshable = true
                    }.onError { exception ->
                        exception.toString()
                        accessToken = ""
                    }
                }
            }
        }
        if(isRefreshable) {
            val newRequest = chain.request().newBuilder().addHeader("Authorization", accessToken)
                .build()
            return chain.proceed(newRequest)
        }

        return response
    }
}