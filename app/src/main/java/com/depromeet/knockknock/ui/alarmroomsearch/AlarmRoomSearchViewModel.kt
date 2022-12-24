package com.depromeet.knockknock.ui.alarmroomsearch

import com.depromeet.knockknock.base.BaseViewModel
import com.depromeet.knockknock.ui.alarmroomsearch.model.AlarmRoom
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmRoomSearchViewModel @Inject constructor(
) : BaseViewModel(), AlarmRoomSearchActionHandler {

    private val TAG = "AlarmRoomSearchViewModel"

    private val _navigationHandler: MutableSharedFlow<AlarmRoomSearchNavigationAction> = MutableSharedFlow<AlarmRoomSearchNavigationAction>()
    val navigationHandler: SharedFlow<AlarmRoomSearchNavigationAction> = _navigationHandler.asSharedFlow()

    val searchQuery: MutableStateFlow<String> = MutableStateFlow<String>("")
    var editTextMessageCountEvent = MutableStateFlow<Int>(0)

    private val _roomIsPublic : MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(true)
    val roomIsPublic : StateFlow<Boolean> = _roomIsPublic.asStateFlow()


    init {
        baseViewModelScope.launch {
            searchQuery.debounce(0).collectLatest {
                onEditTextCount(it.codePointCount(0, it.length))
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

    override fun onRoomClicked(roomId: Int) {
        baseViewModelScope.launch {
            _navigationHandler.emit(AlarmRoomSearchNavigationAction.NavigateToRoom(roomId = roomId))
        }
    }


}