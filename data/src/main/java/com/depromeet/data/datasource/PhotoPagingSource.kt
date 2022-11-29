//package com.example.data.datasource.remote
//
//import androidx.paging.PagingSource
//import androidx.paging.PagingState
//import com.example.data.api.PhotoApi
//import com.example.data.datasource.remote.PhotoDataSourceImpl.Companion.NETWORK_PAGE_SIZE
//import com.example.data.mapper.toDomain
//import com.example.domain.model.Photo
//import retrofit2.HttpException
//import java.io.IOException
//import javax.inject.Inject
//
//private const val PHOTO_STARTING_PAGE_INDEX = 1
//
//class PhotoPagingSource @Inject constructor(
//    private val photoApi: PhotoApi
//) : PagingSource<Int, Photo>() {
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
//        val position = params.key ?: PHOTO_STARTING_PAGE_INDEX
//        return try {
//            val response = photoApi.getPhotoList(position, params.loadSize).toDomain()
//
//            val nextKey = if (response.isEmpty()) {
//                null
//            } else {
//                position + (params.loadSize / NETWORK_PAGE_SIZE)
//            }
//            LoadResult.Page(
//                data = response,
//                prevKey = if (position == PHOTO_STARTING_PAGE_INDEX) null else position - 1,
//                nextKey = nextKey
//            )
//        } catch (exception: IOException) {
//            return LoadResult.Error(exception)
//        } catch (exception: HttpException) {
//            return LoadResult.Error(exception)
//        }
//    }
//    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
//        return state.anchorPosition?.let { anchorPosition ->
//            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
//                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
//        }
//    }
//
//}