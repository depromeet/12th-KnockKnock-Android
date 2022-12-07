package com.depromeet.knockknock.ui.home

import com.depromeet.knockknock.base.BaseViewModel
import com.depromeet.knockknock.ui.notification.NotificationNavigationAction
//import com.dida.android.presentation.views.nav.home.HomeNavigationAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
) : BaseViewModel(), HomeActionHandler {

    private val TAG = "HomeViewModel"

    private val _navigationHandler: MutableSharedFlow<HomeNavigationAction> = MutableSharedFlow<HomeNavigationAction>()
    val navigationHandler: SharedFlow<HomeNavigationAction> = _navigationHandler.asSharedFlow()

    override fun onCreatePushClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(HomeNavigationAction.NavigateToCreatePush)
        }
    }

    override fun onRoomClicked(roomId: Int) {
        baseViewModelScope.launch {
            _navigationHandler.emit(HomeNavigationAction.NavigateToRoom(roomId = roomId))
        }
    }

    override fun onRecentAlarmClicked(alarmId: Int) {
        baseViewModelScope.launch {
            _navigationHandler.emit(HomeNavigationAction.NavigateToRecentAlarm(alarmId = alarmId))
        }
    }

    override fun onNotificationClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(HomeNavigationAction.NavigateToNotification)
        }
    }
}