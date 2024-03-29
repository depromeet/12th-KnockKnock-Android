package com.depromeet.knockknock.ui.alarmroomsearch

import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.depromeet.domain.model.Category
import com.depromeet.domain.model.Friend
import com.depromeet.domain.model.GroupBriefInfo
import com.depromeet.domain.model.GroupContent
import com.depromeet.domain.onSuccess
import com.depromeet.domain.repository.MainRepository
import com.depromeet.knockknock.base.BaseViewModel
import com.depromeet.knockknock.ui.alarmroomexplore.adapter.createAlarmRoomPager
import com.depromeet.knockknock.ui.alarmroomsearch.model.AlarmRoom
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmRoomSearchViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel(), AlarmRoomSearchActionHandler {

    private val TAG = "AlarmRoomSearchViewModel"

    private val _navigationHandler: MutableSharedFlow<AlarmRoomSearchNavigationAction> =
        MutableSharedFlow<AlarmRoomSearchNavigationAction>()
    val navigationHandler: SharedFlow<AlarmRoomSearchNavigationAction> =
        _navigationHandler.asSharedFlow()

    val roomInputState: MutableStateFlow<String> = MutableStateFlow<String>("")
    var editTextMessageCountEvent = MutableStateFlow<Int>(0)

    private val _roomIsPublic: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(true)
    val roomIsPublic: StateFlow<Boolean> = _roomIsPublic.asStateFlow()

    var roomList: Flow<PagingData<GroupContent>> = emptyFlow()
    var filteredRoomList: Flow<PagingData<GroupContent>> = emptyFlow()
    var initCategory: MutableStateFlow<Int> = MutableStateFlow(1)

    private val _popularCategoryList: MutableStateFlow<List<Category>> =
        MutableStateFlow(emptyList())
    val popularCategoryList: StateFlow<List<Category>> = _popularCategoryList.asStateFlow()


    init {
        baseViewModelScope.launch {
            roomInputState.debounce(0).collectLatest {
                onEditTextCount(it.codePointCount(0, it.length))
            }
        }

        roomList = createAlarmRoomPager(
            mainRepository = mainRepository,
            category = initCategory,
        ).flow.cachedIn(baseViewModelScope)

        baseViewModelScope.launch {
            roomInputState.debounce(0).collect {
                roomInputState.emit(it)
            }
        }


        baseViewModelScope.launch {
            mainRepository.getGroupCategoriesFamous()
                .onSuccess {
                    _popularCategoryList.emit(it.categories)
                }
        }
    }

    private fun onEditTextCount(count: Int) {
        baseViewModelScope.launch {
            editTextMessageCountEvent.value = count
        }
    }

    override fun onAlarmRoomEditTextClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(AlarmRoomSearchNavigationAction.NavigateToHidePopularCategory)
        }
    }

    override fun onAlarmRoomGenerateClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(AlarmRoomSearchNavigationAction.NavigateToMakeRoom)
        }

    }
    override fun onRoomClicked(roomId: Int) {
        baseViewModelScope.launch {
            _navigationHandler.emit(AlarmRoomSearchNavigationAction.NavigateToRoom(roomId = roomId))
        }
    }


}