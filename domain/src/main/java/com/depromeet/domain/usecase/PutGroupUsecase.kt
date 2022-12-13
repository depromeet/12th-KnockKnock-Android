package com.depromeet.domain.usecase

import com.depromeet.domain.NetworkResult
import com.depromeet.domain.model.*
import com.depromeet.domain.repository.MainRepository
import javax.inject.Inject


class PutGroupUsecase @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(
        id: Int,
        title: String,
        description: String,
        publicAccess: Boolean,
        thumbnailPath: String,
        backgroundImagePath: String,
        categoryId: Int
    ): NetworkResult<GroupResponse> {
        return repository.putGroup(
            id = id,
            title = title,
            description = description,
            publicAccess = publicAccess,
            thumbnailPath = thumbnailPath,
            backgroundImagePath = backgroundImagePath,
            categoryId = categoryId
        )
    }
}