package com.depromeet.domain.usecase

import com.depromeet.domain.NetworkResult
import com.depromeet.domain.model.GoogleLoginLinkResponse
import com.depromeet.domain.model.KakaoLoginLinkResponse
import com.depromeet.domain.repository.MainRepository
import javax.inject.Inject

class GetGoogleLoginLinkUsecase @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(): NetworkResult<GoogleLoginLinkResponse> {
        return repository.getGoogleLoginLink()
    }
}