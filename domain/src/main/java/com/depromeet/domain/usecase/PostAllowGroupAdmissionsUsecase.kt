package com.depromeet.domain.usecase

import com.depromeet.domain.NetworkResult
import com.depromeet.domain.model.Admissions
import com.depromeet.domain.repository.MainRepository
import javax.inject.Inject


class PostRefuseGroupAdmissionsUsecase @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(id: Int, admissionsId: Int): NetworkResult<Admissions> {
        return repository.postRefuseGroupAdmissions(id = id, admissionsId = admissionsId)
    }
}