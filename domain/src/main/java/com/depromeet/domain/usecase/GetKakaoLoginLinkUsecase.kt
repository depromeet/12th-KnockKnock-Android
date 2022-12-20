package com.depromeet.domain.usecase

import com.depromeet.domain.NetworkResult
import com.depromeet.domain.model.KakaoLoginLinkResponse
import com.depromeet.domain.repository.MainRepository
import javax.inject.Inject

class GetKakaoLoginLinkUsecase @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(): NetworkResult<KakaoLoginLinkResponse> {
        return repository.getKakaoLoginLink()
    }
}