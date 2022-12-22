package com.depromeet.data.model.request

import com.google.gson.annotations.SerializedName


data class DeleteStorageRequest(
    @SerializedName("storage_ids") val storage_ids: List<Int>
)