package com.depromeet.knockknock.ui.alarmroomhistory.adapter
import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.depromeet.domain.fold
import com.depromeet.domain.model.Notification
import com.depromeet.domain.repository.MainRepository
import kotlinx.coroutines.flow.StateFlow

fun createAlarmRoomHistoryBundlePager(
    mainRepository: MainRepository,
    groupId: StateFlow<Int>,
    sort: StateFlow<String>
): Pager<Int, Notification> = Pager(
    config = PagingConfig(pageSize = 10, initialLoadSize = 10, enablePlaceholders = true),
    initialKey = 0,
    pagingSourceFactory = {
        AlarmRoomHistoryBundlePagingSource(
            mainRepository = mainRepository,
            groupId = groupId,
            sort = sort
        )
    }
)
class AlarmRoomHistoryBundlePagingSource(
    private val mainRepository: MainRepository,
    private val groupId: StateFlow<Int>,
    private val sort: StateFlow<String>
) : PagingSource<Int, Notification>() {
    override fun getRefreshKey(state: PagingState<Int, Notification>): Int? = null
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Notification> {
        val pageIndex = params.key ?: 0
        val result = mainRepository.getNotification(
            page = pageIndex,
            size = params.loadSize,
            sort = sort.value,
            group_id = groupId.value,
        )
        Log.d("ttt 알림방 히스토리", result.fold(
            onSuccess = { contents ->
                LoadResult.Page(
                    data = contents.notifications.content,
                    prevKey = null,
                    nextKey = if (!contents.notifications.last) pageIndex + 1 else null
                )
            },
            onError = { e -> LoadResult.Error(e) }
        ).toString())
        return result.fold(
            onSuccess = { contents ->
                LoadResult.Page(
                    data = contents.notifications.content,
                    prevKey = null,
                    nextKey = if (contents.notifications.last) pageIndex + 1 else null
                )
            },
            onError = { e -> LoadResult.Error(e) }
        )
    }
}