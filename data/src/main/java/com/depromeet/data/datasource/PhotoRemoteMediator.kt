package com.example.data.datasource.remote

import androidx.paging.*
import androidx.room.withTransaction
import com.example.data.api.PhotoApi
import com.example.data.db.PhotoDatabase
import com.example.data.db.TodoDatabase
import com.example.data.mapper.toDomain
import com.example.data.model.local.RemoteKeys
import com.example.data.model.remote.PhotoResponse
import com.example.domain.model.Photo
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

private const val PHOTO_STARTING_PAGE_INDEX = 1

@OptIn(ExperimentalPagingApi::class)
class PhotoRemoteMediator @Inject constructor(
    private val photoDatabase: PhotoDatabase,
    private val photoApi: PhotoApi
) : RemoteMediator<Int, Photo>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Photo>): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: PHOTO_STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                if (prevKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                if (nextKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                nextKey
            }
        }

        try {
            val apiResponse = photoApi.getPhotoList(page, state.config.pageSize)
            val endOfPaginationReached = apiResponse.isEmpty()

            photoDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    photoDatabase.remoteKeyDao().clearRemoteKeys()
                    photoDatabase.photoDao().clearPhotos()
                }
                val prevKey = if (page == PHOTO_STARTING_PAGE_INDEX) null else page-1
                val nextKey = if (endOfPaginationReached) null else page+1
                val keys = apiResponse.map {
                    RemoteKeys(photoId = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                photoDatabase.remoteKeyDao().insertAll(keys)
                photoDatabase.photoDao().insertAll(apiResponse)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Photo>
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { photoId ->
                photoDatabase.remoteKeyDao().remoteKeysRepoId(photoId)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Photo>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { photo ->
                photoDatabase.remoteKeyDao().remoteKeysRepoId(photo.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Photo>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { repo ->
                photoDatabase.remoteKeyDao().remoteKeysRepoId(repo.id)
            }
    }
}
