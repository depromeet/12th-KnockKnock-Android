package com.depromeet.knockknock.ui.bookmark.adapter

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.depromeet.domain.fold
import com.depromeet.domain.model.Notification
import com.depromeet.domain.repository.MainRepository

fun createNotificationPager(
    mainRepository: MainRepository
): Pager<Int, Notification> = Pager(
    config = PagingConfig(pageSize = 10, initialLoadSize = 10, enablePlaceholders = true),
    initialKey = 0,
    pagingSourceFactory = { NotificationPagingSource(mainRepository = mainRepository) }
)

class NotificationPagingSource(
    private val mainRepository: MainRepository
) : PagingSource<Int, Notification>() {

    override fun getRefreshKey(state: PagingState<Int, Notification>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Notification> {
        val pageIndex = params.key ?: 0
        val result = mainRepository.getStroages(
            page = pageIndex,
            size = params.loadSize
        )
        return result.fold(
            onSuccess = { contents ->
                LoadResult.Page(
                    data = contents.content,
                    prevKey = null,
                    nextKey = if (contents.last == true) pageIndex + 1 else null
                )
            },
            onError = { e -> LoadResult.Error(e) }
        )
    }

}