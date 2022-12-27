package com.depromeet.knockknock.ui.settingroomforuser

import com.depromeet.knockknock.base.BaseViewModel
import com.depromeet.knockknock.ui.alarmroomsearch.model.AlarmRoom
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.*

@HiltViewModel
class SettingRoomForUserViewModel @Inject constructor(
) : BaseViewModel(), SettingRoomForUserActionHandler {

    private val TAG = "SettingRoomViewModel"

    private val _navigationHandler: MutableSharedFlow<SettingRoomForUserNavigationAction> =
        MutableSharedFlow<SettingRoomForUserNavigationAction>()
    val navigationHandler: SharedFlow<SettingRoomForUserNavigationAction> =
        _navigationHandler.asSharedFlow()

    private val _categoryInfo: MutableStateFlow<String> = MutableStateFlow<String>("없음")
    val categoryInfo: StateFlow<String> = _categoryInfo.asStateFlow()

    private val _roomInfo: MutableStateFlow<AlarmRoom> = MutableStateFlow<AlarmRoom>(
        AlarmRoom(
            roomId = 1,
            roomName = "테스트1호",
            roomBackground = "https://t1.daumcdn.net/cfile/tistory/996333405A8280FC23",
            roomThumbnail = "https://t1.daumcdn.net/cfile/tistory/996333405A8280FC23",
            roomDescription = "무슨무슨 방입니다",
            roomCategoryName = "스터디",
            roomMemberCount = 23
        )
    )
    val roomInfo: StateFlow<AlarmRoom> = _roomInfo.asStateFlow()



    override fun onAddMemberClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(SettingRoomForUserNavigationAction.NavigateToAddMember(roomId = roomInfo.value.roomId))
        }
    }


    override fun onLinkClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(SettingRoomForUserNavigationAction.NavigateToLink(roomId = roomInfo.value.roomId))
        }
    }

}