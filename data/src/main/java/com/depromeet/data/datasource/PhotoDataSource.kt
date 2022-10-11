package com.example.data.datasource.remote

import androidx.paging.PagingData
import com.example.data.model.remote.PhotoResponse
import com.example.domain.BaseResult
import com.example.domain.model.Photo
import kotlinx.coroutines.flow.Flow

interface PhotoDataSource {
    suspend fun getPhotoList(): BaseResult<Flow<PagingData<Photo>>>

    suspend fun getPhotoListWithDB(): BaseResult<Flow<PagingData<Photo>>>
}
