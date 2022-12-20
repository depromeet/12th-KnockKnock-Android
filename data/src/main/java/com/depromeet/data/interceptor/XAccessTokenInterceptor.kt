package com.depromeet.data.interceptor

import com.depromeet.data.DataApplication.Companion.dataStorePreferences
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class XAccessTokenInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()

        runBlocking {
            dataStorePreferences.getAccessToken()?.let { accessToken ->
                builder.addHeader("Bearer ", accessToken)
                return@let chain.proceed(builder.build())
            }
        }

        return chain.proceed(chain.request())
    }
}