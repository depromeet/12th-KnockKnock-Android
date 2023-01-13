package com.depromeet.data.interceptor

import com.depromeet.data.model.error.*
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import javax.net.ssl.SSLHandshakeException

class ErrorResponseInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        try {
            val response = chain.proceed(request)
            val responseBody = response.body

            if (response.isSuccessful) return response

            val requestUrl = request.url.toString()
            val errorResponse = responseBody?.string()?.let { createErrorResponse(it) }
            val errorException = createErrorException(requestUrl, response.code, errorResponse)
            errorException?.let { throw it }

            return response
        } catch (e: Throwable) {
            /**
             * Non-IOException subtypes thrown from interceptor never notify Callback
             * See https://github.com/square/okhttp/issues/5151
             */

            when (e) {
                is IOException,
                is SSLHandshakeException -> throw e
                else -> throw IOException(e)
            }
        }
    }
}

fun createErrorResponse(responseBodyString: String): ErrorResponseImpl? =
    try {
        Gson().newBuilder().create().getAdapter(ErrorResponseImpl::class.java).fromJson(responseBodyString)
    } catch (e: Exception) {
        null
    }

fun createErrorException(url: String?, httpCode: Int, errorResponse: ErrorResponseImpl?): Exception? =
    when (httpCode) {
        400 -> BadRequestException(Throwable(errorResponse?.reason), url)
        401 -> InvalidAccessTokenExpire(Throwable(errorResponse?.reason), url)
        403 -> InvalidAccessTokenException(Throwable(errorResponse?.reason), url)
        404 -> ServerNotFoundException(Throwable(errorResponse?.reason), url)
        500 -> InternalServerErrorException(Throwable(errorResponse?.reason), url)
        else -> null
    }
