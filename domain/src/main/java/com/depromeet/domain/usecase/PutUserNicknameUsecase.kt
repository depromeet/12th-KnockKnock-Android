package com.depromeet.domain.usecase

import com.depromeet.domain.NetworkResult
import com.depromeet.domain.model.GoogleLoginResponse
import com.depromeet.domain.model.KakaoLoginResponse
import com.depromeet.domain.model.RefreshTokenResponse
import com.depromeet.domain.model.SearchUserNicknameResponse
import com.depromeet.domain.repository.MainRepository
import javax.inject.Inject


class PutUserNicknameUsecase @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(nickName: String): NetworkResult<Unit> {
        return repository.putUserNickname(nickName = nickName)
    }
}