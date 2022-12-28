package com.depromeet.knockknock.ui.home

import com.depromeet.domain.onSuccess
import com.depromeet.domain.repository.MainRepository
import com.depromeet.knockknock.base.BaseViewModel
import com.depromeet.knockknock.ui.bookmark.model.Room
import com.depromeet.knockknock.ui.notification.NotificationNavigationAction
import com.depromeet.knockknock.util.randomNum
//import com.dida.android.presentation.views.nav.home.HomeNavigationAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel(), HomeActionHandler {

    private val TAG = "HomeViewModel"

    private val _navigationHandler: MutableSharedFlow<HomeNavigationAction> = MutableSharedFlow<HomeNavigationAction>()
    val navigationHandler: SharedFlow<HomeNavigationAction> = _navigationHandler.asSharedFlow()

    private val _roomList: MutableStateFlow<List<Room>> = MutableStateFlow<List<Room>>(emptyList())
    val roomList: StateFlow<List<Room>> = _roomList.asStateFlow()

    val homeRandomNumber: Int = randomNum()
    val recommendText: String = "똑똑!\n오늘은 누구의 문을\n두드려 볼까!?"

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

    override fun onReactionClicked(alarmId: Int) {
        baseViewModelScope.launch {
            _navigationHandler.emit(HomeNavigationAction.NavigateToRecentAlarm(alarmId = alarmId))
        }
    }

    override fun onNotificationClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(HomeNavigationAction.NavigateToNotification)
        }
    }

    override fun onSearchRoomClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(HomeNavigationAction.NavigateToSearchRoom)
        }
    }

    override fun onCreateRoomClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(HomeNavigationAction.NavigateToCreateRoom)
        }
    }

    override fun onRecentAlarmMoreClicked(alarmId: Int) {
        baseViewModelScope.launch {
            _navigationHandler.emit(HomeNavigationAction.NavigateToRecentAlarmMore(alarmId = alarmId))
        }
    }
}