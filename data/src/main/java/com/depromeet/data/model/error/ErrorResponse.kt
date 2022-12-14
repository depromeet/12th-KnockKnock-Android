package com.depromeet.data.model.error

interface ErrorResponse {
    val timestamp: String?
    val status: Int?
    val message: String?
    val code: Int?
}