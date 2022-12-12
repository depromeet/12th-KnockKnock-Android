package com.depromeet.domain.usecase

import com.depromeet.domain.NetworkResult
import com.depromeet.domain.model.*
import com.depromeet.domain.repository.MainRepository
import javax.inject.Inject


class GetGroupAdmissionsUsecase @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(id: Int): NetworkResult<GroupAdmissionsResponse> {
        return repository.getGroupAdmissions(id = id)
    }
}