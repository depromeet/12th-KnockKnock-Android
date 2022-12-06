package com.depromeet.knockknock.ui.notification

sealed class NotificationNavigationAction {
    object NavigateToInviteRoomAllow: NotificationNavigationAction()
    object NavigateToInviteRoomDeclare: NotificationNavigationAction()
    object NavigateToInviteFriendAllow: NotificationNavigationAction()
    object NavigateToInviteFriendDeclare: NotificationNavigationAction()
    class NavigateToNotificationDetail(val notificationId: Int): NotificationNavigationAction()
}