package com.depromeet.knockknock.ui.notification

import com.depromeet.domain.model.Alarm
import com.depromeet.domain.onSuccess
import com.depromeet.domain.repository.MainRepository
import com.depromeet.knockknock.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel(), NotificationActionHandler {

    private val TAG = "NotificationViewModel"

    private val _navigationHandler: MutableSharedFlow<NotificationNavigationAction> = MutableSharedFlow<NotificationNavigationAction>()
    val navigationHandler: SharedFlow<NotificationNavigationAction> = _navigationHandler.asSharedFlow()

    private val _notificationList: MutableStateFlow<List<Alarm>> = MutableStateFlow<List<Alarm>>(emptyList())
    val notificationList: StateFlow<List<Alarm>> = _notificationList.asStateFlow()

    init {
        baseViewModelScope.launch {
            showLoading()
            mainRepository.getAlarms()
                .onSuccess { _notificationList.value = it.list }
            dismissLoading()
        }
    }

    override fun onInviteRoomAllowClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(NotificationNavigationAction.NavigateToInviteRoomAllow)
        }
    }

    override fun onInviteRoomDeclareClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(NotificationNavigationAction.NavigateToInviteRoomDeclare)
        }
    }

    override fun onInviteFriendAllowClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(NotificationNavigationAction.NavigateToInviteFriendAllow)
        }
    }

    override fun onInviteFriendDeclareClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(NotificationNavigationAction.NavigateToInviteFriendDeclare)
        }
    }

    override fun onNotificationClicked(notificationId: Int) {
        baseViewModelScope.launch {
            _navigationHandler.emit(NotificationNavigationAction.NavigateToNotificationDetail(notificationId))
        }
    }

    override fun onEmptyBtnClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(NotificationNavigationAction.NavigateToPushNotification)
        }
    }

}