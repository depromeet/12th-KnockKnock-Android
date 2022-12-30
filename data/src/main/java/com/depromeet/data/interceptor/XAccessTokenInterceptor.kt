package com.depromeet.data.interceptor

import com.depromeet.data.DataApplication
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class XAccessTokenInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()

        try {
            DataApplication.sSharedPreferences.getString("access_token", null).let { token ->
                token?.let {
                    builder.addHeader("Authorization", "Bearer $it")
                    return chain.proceed(builder.build())
                }
            }
        } catch (e: Exception) {
            return chain.proceed(chain.request())
        }
        return chain.proceed(chain.request())
    }
}