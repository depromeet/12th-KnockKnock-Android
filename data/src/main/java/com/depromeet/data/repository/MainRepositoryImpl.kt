package com.depromeet.data.repository

import com.depromeet.data.api.MainAPIService
import com.depromeet.data.api.handleApi
import com.depromeet.data.mapper.toDomain
import com.depromeet.data.model.request.*
import com.depromeet.domain.NetworkResult
import com.depromeet.domain.model.*
import com.depromeet.domain.repository.MainRepository
import javax.inject.Inject
import javax.inject.Named

class MainRepositoryImpl @Inject constructor(
    @Named("Main") private val mainAPIService: MainAPIService
): MainRepository {
}