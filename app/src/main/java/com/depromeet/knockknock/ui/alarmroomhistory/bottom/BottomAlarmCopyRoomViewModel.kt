package com.depromeet.knockknock.ui.alarmroomhistory.bottom

import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.depromeet.domain.model.GroupContent
import com.depromeet.domain.repository.MainRepository
import com.depromeet.knockknock.base.BaseViewModel
import com.depromeet.knockknock.ui.bookmark.adapter.createFilterRoomPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BottomAlarmCopyRoomViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel(), BottomAlarmCopyRoomActionHandler {

    private val TAG = "BottomRoomViewModel"

    var clickRoomList = 0

    val roomList: Flow<PagingData<GroupContent>> = createFilterRoomPagingSource(mainRepository = mainRepository).flow.cachedIn(baseViewModelScope)

    private val _isSelected: MutableSharedFlow<Unit> = MutableSharedFlow()
    val isSelected: SharedFlow<Unit> = _isSelected.asSharedFlow()

    override fun onRoomClicked(group_id: Int) {
        baseViewModelScope.launch {
            clickRoomList = group_id
            _isSelected.emit(Unit)
        }
    }
}