package com.depromeet.knockknock.ui.home.bottom.select

import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.depromeet.domain.model.GroupContent
import com.depromeet.domain.model.Notification
import com.depromeet.domain.repository.MainRepository
import com.depromeet.knockknock.base.BaseViewModel
import com.depromeet.knockknock.ui.bookmark.adapter.createNotificationPager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BottomHomeSelectViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel(),BottomRoomSelectActionHandler {

    private val TAG = "BottomHomeSelectViewModel"

    val roomList: Flow<PagingData<GroupContent>> = createBottomRoomSelectPagingSourcePager(
        mainRepository = mainRepository
    ).flow.cachedIn(baseViewModelScope)

    private val _selectRoom: MutableStateFlow<Int> = MutableStateFlow(0)
    val selectRoom: StateFlow<Int> = _selectRoom.asStateFlow()

    val isEmptyList: MutableStateFlow<Boolean> = MutableStateFlow(true)

    override fun onRoomClicked(roomId: Int) {
        baseViewModelScope.launch {
            _selectRoom.value = roomId
        }
    }

    override fun onRoomSearchClicked() {
        baseViewModelScope.launch {
            _selectRoom.value = -1
        }
    }

    override fun onCreateRoomClicked() {
        baseViewModelScope.launch {
            _selectRoom.value = -2
        }
    }

}