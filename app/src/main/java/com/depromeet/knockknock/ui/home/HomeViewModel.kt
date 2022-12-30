package com.depromeet.knockknock.ui.home

import com.depromeet.domain.model.Notification
import com.depromeet.domain.onError
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

    private val _notifications: MutableStateFlow<List<Notification>> = MutableStateFlow(emptyList())
    val notifications: StateFlow<List<Notification>> = _notifications.asStateFlow()

    private val _existedAlarm: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val existedAlarm: StateFlow<Boolean> = _existedAlarm.asStateFlow()

    init {
        baseViewModelScope.launch {
            mainRepository.getAlarmsCount()
                .onSuccess {
                    _existedAlarm.value = it.count > 0 }
        }
    }

    fun getRecentNotifications() {
        baseViewModelScope.launch {
            mainRepository.getNotifications()
                .onSuccess { _notifications.value = it.notifications }
        }
    }

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

    override fun onRecentAlarmMoreClicked(alarmId: Int) {
        baseViewModelScope.launch {
            _navigationHandler.emit(HomeNavigationAction.NavigateToRecentAlarmMore(alarmId = alarmId))
        }
    }
}