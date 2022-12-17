package com.depromeet.data.model.error

import com.google.gson.annotations.SerializedName

data class ErrorResponseImpl(
    @SerializedName("timestamp") override val timestamp: String?,
    @SerializedName("status") override val status: Int?,
    @SerializedName("message") override val message: String?,
    @SerializedName("code") override val code: Int?
) : ErrorResponse


