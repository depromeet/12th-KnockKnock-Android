package com.depromeet.knockknock.ui.alarmroomhistory.adapter

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.depromeet.domain.fold
import com.depromeet.domain.model.Notification
import com.depromeet.domain.onSuccess
import com.depromeet.domain.repository.MainRepository
import com.depromeet.knockknock.ui.alarmroomhistory.AlarmRoomHistoryViewModel
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun createAlarmRoomHistoryBundlePager(
    mainRepository: MainRepository,
    groupId: StateFlow<Int>,
    sort: StateFlow<String>,
    viewModel: AlarmRoomHistoryViewModel
): Pager<Int, Notification> = Pager(
    config = PagingConfig(pageSize = 10, initialLoadSize = 10, enablePlaceholders = true),
    initialKey = 0,
    pagingSourceFactory = {
        AlarmRoomHistoryBundlePagingSource(
            mainRepository = mainRepository,
            groupId = groupId,
            sort = sort,
            viewModel = viewModel
        )
    }
)

class AlarmRoomHistoryBundlePagingSource(
    private val mainRepository: MainRepository,
    private val groupId: StateFlow<Int>,
    private val sort: StateFlow<String>,
    private val viewModel: AlarmRoomHistoryViewModel

) : PagingSource<Int, Notification>() {

    override fun getRefreshKey(state: PagingState<Int, Notification>): Int? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Notification> {
        val pageIndex = params.key ?: 0

        val result = mainRepository.getNotification(
            page = pageIndex,
            size = params.loadSize,
            sort = sort.value,
            group_id = groupId.value,

            ).onSuccess {
            if (it.reservations != null) {
                val formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")
                val currentDate = LocalDate.now().format(formatter)
                val createdDate =
                    LocalDate.parse(it.reservations!!.send_at.substring(0, 10), DateTimeFormatter.ISO_DATE)
                        .format(formatter)

                if (currentDate == createdDate){
                    viewModel.reservationTimeEvent.value = LocalDateTime.parse(it.reservations!!.send_at, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                        .format(DateTimeFormatter.ofPattern("오늘 HH:mm 발송 예정"))
                }else{
                    viewModel.reservationTimeEvent.value = LocalDateTime.parse(it.reservations!!.send_at, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                        .format(DateTimeFormatter.ofPattern("MM월 dd일 HH:mm 발송 예정"))
                }
                viewModel.reservationMessageImgUri.value = it.reservations!!.image_url
                viewModel.reservationMessageEvent.value = it.reservations!!.content
            }
        }

        return result.fold(
            onSuccess = { contents ->
                LoadResult.Page(
                    data = contents.notifications.content,
                    prevKey = null,
                    nextKey = if (!contents.notifications.last) pageIndex + 1 else null
                )
            },
            onError = { e -> LoadResult.Error(e) }
        )
    }
}