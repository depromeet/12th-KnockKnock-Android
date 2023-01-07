package com.depromeet.knockknock.ui.bookmark.adapter

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.depromeet.domain.fold
import com.depromeet.domain.model.Notification
import com.depromeet.domain.repository.MainRepository
import kotlinx.coroutines.flow.StateFlow

fun createNotificationPager(
    mainRepository: MainRepository,
    groupids: StateFlow<List<Int>>,
    periods: StateFlow<Int>
): Pager<Int, Notification> = Pager(
    config = PagingConfig(pageSize = 10, initialLoadSize = 10, enablePlaceholders = true),
    initialKey = 0,
    pagingSourceFactory = {
        NotificationPagingSource(
            mainRepository = mainRepository,
            groupids = groupids,
            periods = periods
        )
    }
)

class NotificationPagingSource(
    private val mainRepository: MainRepository,
    private val groupids: StateFlow<List<Int>>,
    private val periods: StateFlow<Int>
) : PagingSource<Int, Notification>() {

    override fun getRefreshKey(state: PagingState<Int, Notification>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Notification> {
        val pageIndex = params.key ?: 0
        val result = mainRepository.getStroages(
            page = pageIndex,
            size = params.loadSize,
            groupId = groupids.value,
            periodOfMonth = if(periods.value == 0) null else periods.value
        )
        return result.fold(
            onSuccess = { contents ->
                LoadResult.Page(
                    data = contents.content,
                    prevKey = null,
                    nextKey = if (!contents.last) pageIndex + 1 else null
                )
            },
            onError = { e -> LoadResult.Error(e) }
        )
    }

}