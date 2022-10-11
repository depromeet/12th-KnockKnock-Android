package com.depromeet.data.repository

import com.depromeet.data.api.MainAPIService
import com.depromeet.domain.repository.MainRepository
import javax.inject.Inject
import javax.inject.Named

class MainRepositoryImpl @Inject constructor(
    @Named("Main") private val mainAPIService: MainAPIService
): MainRepository {

//    override suspend fun checkVersionAPI(): NetworkResult<AppVersionResponse> {
//        return handleApi { mainAPIService.checkVersion() }
//    }
}