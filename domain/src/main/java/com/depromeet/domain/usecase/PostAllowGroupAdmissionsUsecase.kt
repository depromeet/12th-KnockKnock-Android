package com.depromeet.domain.usecase

import com.depromeet.domain.NetworkResult
import com.depromeet.domain.model.Admissions
import com.depromeet.domain.repository.MainRepository
import javax.inject.Inject


class PostAllowGroupAdmissionsUsecase @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(id: Int, admissionsId: Int): NetworkResult<Admissions> {
        return repository.postAllowGroupAdmissions(id = id, admissionsId = admissionsId)
    }
}