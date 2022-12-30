package com.depromeet.knockknock.ui.alarmroomtab

import com.depromeet.knockknock.base.BaseViewModel
import com.depromeet.knockknock.ui.friendlist.FriendListNavigationAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmRoomTabViewModel @Inject constructor(
) : BaseViewModel(), AlarmRoomTabActionHandler{

    private val TAG = "AlarmRoomSearchViewModel"

    private val _navigationHandler: MutableSharedFlow<AlarmRoomTabNavigationAction> = MutableSharedFlow<AlarmRoomTabNavigationAction>()
    val navigationHandler: SharedFlow<AlarmRoomTabNavigationAction> = _navigationHandler.asSharedFlow()


    override fun onMakeAlarmRoomClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(AlarmRoomTabNavigationAction.NavigateToMakeRoomBottomSheet)
        }
    }


}