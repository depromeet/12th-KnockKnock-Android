package com.depromeet.domain.model


data class GroupAdmissionsResponse(
    val admissions: List<Admissions>
)
data class Admissions(
    val user: User,
    val admissionState: String,
    val id: Int,
    val createdAt: String
)

data class User(
    val userId: Int,
    val nickname: String,
    val profilePath: String
)