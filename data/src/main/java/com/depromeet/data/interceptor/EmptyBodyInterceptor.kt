package com.depromeet.data.interceptor

import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody

class EmptyBodyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        if (response.isSuccessful.not() || response.code.let { it != 204 && it != 205 && it != 201}) {
            return response
        }

        if ((response.body?.contentLength() ?: -1) >= 0) {
            return response.newBuilder().code(200).build()
        }

        val emptyBody = "".toResponseBody("text/plain".toMediaType())

        return response
            .newBuilder()
            .code(200)
            .body(emptyBody)
            .build()
    }
}