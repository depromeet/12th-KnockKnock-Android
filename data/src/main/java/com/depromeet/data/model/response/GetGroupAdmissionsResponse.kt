package com.depromeet.data.model.response

import com.depromeet.domain.model.Admissions
import com.google.gson.annotations.SerializedName

data class GetGroupAdmissionsResponse(
    @SerializedName("admissions") val admissions: List<Admissions>
)