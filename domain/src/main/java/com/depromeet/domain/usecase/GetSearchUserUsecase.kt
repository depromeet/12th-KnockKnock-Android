package com.depromeet.domain.usecase

import com.depromeet.domain.NetworkResult
import com.depromeet.domain.model.*
import com.depromeet.domain.repository.MainRepository
import javax.inject.Inject


class GetSearchUserUsecase @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(nickname: String): NetworkResult<UserListResponse> {
        return repository.getSearchUser(nickName = nickname)
    }
}