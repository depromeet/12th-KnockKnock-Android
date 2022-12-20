package com.depromeet.domain.usecase

import com.depromeet.domain.NetworkResult
import com.depromeet.domain.model.*
import com.depromeet.domain.repository.MainRepository
import javax.inject.Inject


class PutUserProfileUsecase @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(nickname: String, profilePath: String): NetworkResult<UserProfileResponse> {
        return repository.putUserProfile(nickName = nickname, profilePath = profilePath)
    }
}