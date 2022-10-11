package com.depromeet.domain.usecase

import com.depromeet.domain.repository.MainRepository
import javax.inject.Inject


class CheckVersionAPI @Inject constructor(
    private val repository: MainRepository
){
//    suspend operator fun invoke(): NetworkResult<AppVersionResponse> {
//        return repository.checkVersionAPI()
//    }
}