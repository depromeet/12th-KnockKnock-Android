package com.depromeet.knockknock.ui.setting_room

import com.depromeet.knockknock.base.BaseViewModel
import com.depromeet.knockknock.ui.bookmark.model.Room
import com.depromeet.knockknock.ui.editprofile.EditProfileNavigationAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.depromeet.knockknock.ui.setprofile.SetProfileNavigationAction
import kotlinx.coroutines.flow.*

@HiltViewModel
class SettingRoomViewModel @Inject constructor(
) : BaseViewModel(), SettingRoomActionHandler {

    private val TAG = "SettingRoomViewModel"

    private val _navigationHandler: MutableSharedFlow<SettingRoomNavigationAction> = MutableSharedFlow<SettingRoomNavigationAction>()
    val navigationHandler: SharedFlow<SettingRoomNavigationAction> = _navigationHandler.asSharedFlow()

    private val _roomInfo: MutableStateFlow<Room> = MutableStateFlow<Room>(Room(roomId = 1, roomImg = "null", roomName = "테스트1호", ))
    val roomInfo: StateFlow<Room> = _roomInfo.asStateFlow()

    override fun onCategoryClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(SettingRoomNavigationAction.NavigateToCategory)
        }
    }

    override fun onEditDetailClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(SettingRoomNavigationAction.NavigateToEditDetail(roomId = roomInfo.value.roomId))
        }
    }

    override fun onAddMemberClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(SettingRoomNavigationAction.NavigateToAddMember(roomId = roomInfo.value.roomId))
        }
    }

    override fun onDeleteClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(SettingRoomNavigationAction.NavigateToAddMember(roomId = roomInfo.value.roomId))
        }
    }

    override fun onLinkClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(SettingRoomNavigationAction.NavigateToLink(roomId = roomInfo.value.roomId))
        }
    }

    override fun onExportClicked(userId: Int) {
        baseViewModelScope.launch {
            _navigationHandler.emit(SettingRoomNavigationAction.NavigateToExportMember(userId = userId))
        }
    }

}