package com.depromeet.domain.usecase

import com.depromeet.domain.NetworkResult
import com.depromeet.domain.model.*
import com.depromeet.domain.repository.MainRepository
import javax.inject.Inject


class PostOpenGroupUsecase @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(category: Int): NetworkResult<OpenGroupsResponse> {
        return repository.getOpenGroups(category = category)
    }
}