package com.example.data.datasource.remote

import androidx.paging.*
import com.example.data.api.PhotoApi
import com.example.data.db.PhotoDatabase
import com.example.data.db.TodoDatabase
import com.example.data.handleResult
import com.example.data.mapper.toDomain
import com.example.domain.BaseResult
import com.example.domain.model.Photo
import com.example.domain.onSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PhotoDataSourceImpl @Inject constructor(
    private val photoApi: PhotoApi,
    private val photoDatabase: PhotoDatabase
    ) : PhotoDataSource {

    override suspend fun getPhotoList(): BaseResult<Flow<PagingData<Photo>>> {
        return handleResult {
            Pager(
                config = PagingConfig(
                    pageSize = NETWORK_PAGE_SIZE,
                    enablePlaceholders = false
                ),
                pagingSourceFactory = { PhotoPagingSource(photoApi) }
            ).flow
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getPhotoListWithDB(): BaseResult<Flow<PagingData<Photo>>> {
        val pagingSourceFactory = { photoDatabase.photoDao().getPhotoList() }
        return handleResult {
            Pager(
                config = PagingConfig(
                    pageSize = NETWORK_PAGE_SIZE,
                    enablePlaceholders = false
                ),
                remoteMediator = PhotoRemoteMediator(
                    photoApi = photoApi,
                    photoDatabase = photoDatabase
                ),
                pagingSourceFactory = pagingSourceFactory
            ).flow
        }
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }
}