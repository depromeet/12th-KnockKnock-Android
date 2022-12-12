package com.depromeet.domain.usecase

import com.depromeet.domain.NetworkResult
import com.depromeet.domain.model.*
import com.depromeet.domain.repository.MainRepository
import javax.inject.Inject


class PostCategoriesUsecase @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(
        emoji: String,
        content: String
    ): NetworkResult<Category> {
        return repository.postCategories(emoji = emoji, content = content)
    }
}