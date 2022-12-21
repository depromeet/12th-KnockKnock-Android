package com.depromeet.data.interceptor

import com.depromeet.data.model.error.InvalidAccessTokenException
import okhttp3.Interceptor
import okhttp3.Response
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
        var accessToken = ""
        val request = chain.request()
        val response = chain.proceed(request)
        if(response.code == 400) {
            val errorResponse = response.body?.string()?.let { createErrorResponse(it) }
            val errorException = createErrorException(
                url = request.url.toString(),
                httpCode = response.code,
                errorResponse = errorResponse
            )
            if(errorException is InvalidAccessTokenException) {
//                runBlocking {
//                    //토큰 갱신 api 호출
//                    DataApplication.dataStorePreferences.getRefreshToken()?.let {
//                        val request = PostRefreshTokenRequest(it)
//
//                        val result = handleApi {
//                            Retrofit.Builder()
//                                .baseUrl(BASE_URL)
//                                .addConverterFactory(GsonConverterFactory.create())
//                                .build()
//                                .create(MainAPIService::class.java).postRefreshToken(request)
//                        }
//
//                        result.onSuccess { response ->
//                            response.accessToken.let { token ->
//                                DataApplication.dataStorePreferences.setOauthToken(token, response.refreshToken)
//                                accessToken = token } }
//                            .onError { accessToken = "" }
//                    }
//                }
            }
            val newRequest = chain.request().newBuilder().addHeader("Bearer ", accessToken)
                .build()
            return chain.proceed(newRequest)
        }
        return response
    }
}