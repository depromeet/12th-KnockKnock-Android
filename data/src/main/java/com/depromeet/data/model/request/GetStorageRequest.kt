package com.depromeet.data.model.request

import com.google.gson.annotations.SerializedName


data class GetStorageRequest(
    @SerializedName("group_ids") val group_ids: List<Int>
)