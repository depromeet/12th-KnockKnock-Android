package com.depromeet.data.model.response

import com.depromeet.domain.model.Category
import com.google.gson.annotations.SerializedName

data class GetCategoriesResponse(
    @SerializedName("categories") val categories: List<Category>
)