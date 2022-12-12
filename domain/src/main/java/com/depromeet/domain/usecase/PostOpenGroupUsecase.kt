package com.depromeet.domain.usecase

import com.depromeet.domain.NetworkResult
import com.depromeet.domain.model.*
import com.depromeet.domain.repository.MainRepository
import javax.inject.Inject


class PostOpenGroupUsecase @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(
        title: String,
        description: String,
        publicAccess: Boolean,
        thumbnailPath: String,
        backgroundImagePath: String,
        categoryId: Int,
        members: List<Int>
    ): NetworkResult<GroupResponse> {
        return repository.postOpenGroups(
            title = title,
            description = description,
            publicAccess = publicAccess,
            thumbnailPath = thumbnailPath,
            backgroundImagePath = backgroundImagePath,
            categoryId = categoryId,
            members = members
        )
    }
}