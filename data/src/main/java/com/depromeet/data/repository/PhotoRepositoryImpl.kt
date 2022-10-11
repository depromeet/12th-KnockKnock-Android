package com.depromeet.data.repository

import androidx.paging.PagingData
import com.example.data.datasource.remote.PhotoDataSource
import com.example.domain.BaseResult
import com.example.domain.model.Photo
import com.example.domain.repository.PhotoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val photoDataSource: PhotoDataSource
) : PhotoRepository {

    override suspend fun getPhotoList(): BaseResult<Flow<PagingData<Photo>>> {
        return photoDataSource.getPhotoList()
    }

    override suspend fun getPhotoListWithDB(): BaseResult<Flow<PagingData<Photo>>> {
        return photoDataSource.getPhotoListWithDB()
    }
}