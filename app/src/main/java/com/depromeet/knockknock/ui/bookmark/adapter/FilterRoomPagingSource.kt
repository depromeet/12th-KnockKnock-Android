package com.depromeet.knockknock.ui.bookmark.adapter

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.depromeet.domain.fold
import com.depromeet.domain.model.GroupContent
import com.depromeet.domain.model.Notification
import com.depromeet.domain.repository.MainRepository
import kotlinx.coroutines.flow.StateFlow

fun createFilterRoomPagingSource(
    mainRepository: MainRepository,
): Pager<Int, GroupContent> = Pager(
    config = PagingConfig(pageSize = 10, initialLoadSize = 10, enablePlaceholders = true),
    initialKey = 0,
    pagingSourceFactory = {
        FilterRoomPagingSource(
            mainRepository = mainRepository
        )
    }
)

class FilterRoomPagingSource(
    private val mainRepository: MainRepository
) : PagingSource<Int, GroupContent>() {

    override fun getRefreshKey(state: PagingState<Int, GroupContent>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GroupContent> {
        val pageIndex = params.key ?: 0
        val result = mainRepository.getJoinedGroups(
            type = "ALL",
            page = pageIndex,
            size = params.loadSize,
        )
        return result.fold(
            onSuccess = { contents ->
                LoadResult.Page(
                    data = contents.groupContent,
                    prevKey = null,
                    nextKey = if (!contents.last) pageIndex + 1 else null
                )
            },
            onError = { e -> LoadResult.Error(e) }
        )
    }

}