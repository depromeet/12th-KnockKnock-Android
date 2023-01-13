package com.depromeet.data.model.error

interface ErrorResponse {
    val success: Boolean
    val status: Int
    val code: String
    val reason: String
    val time_stamp: String
    val path: String
}

//{
//    "success": false,
//    "status": 403,
//    "code": "GLOBAL-403-1",
//    "reason": "refreshToken expired.",
//    "time_stamp": "2022-12-30T17:06:01.930619",
//    "path": "http://localhost:8080/api/v1/credentials/refresh"
//}