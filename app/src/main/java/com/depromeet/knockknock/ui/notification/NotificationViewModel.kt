package com.depromeet.knockknock.ui.notification

import com.depromeet.knockknock.base.BaseViewModel
import com.depromeet.knockknock.ui.friendlist.FriendListNavigationAction
import com.depromeet.knockknock.ui.notification.model.Notification
//import com.dida.android.presentation.views.nav.home.HomeNavigationAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
) : BaseViewModel(), NotificationActionHandler {

    private val TAG = "NotificationViewModel"

    private val _navigationHandler: MutableSharedFlow<NotificationNavigationAction> = MutableSharedFlow<NotificationNavigationAction>()
    val navigationHandler: SharedFlow<NotificationNavigationAction> = _navigationHandler.asSharedFlow()

    private val _notificationList: MutableStateFlow<List<Notification>> = MutableStateFlow<List<Notification>>(emptyList())
    val notificationList: StateFlow<List<Notification>> = _notificationList.asStateFlow()

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