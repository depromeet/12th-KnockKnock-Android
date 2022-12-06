package com.depromeet.knockknock.ui.notification

interface NotificationActionHandler {
    fun onInviteRoomAllowClicked()
    fun onInviteRoomDeclareClicked()
    fun onInviteFriendAllowClicked()
    fun onInviteFriendDeclareClicked()
    fun onNotificationClicked(notificationId: Int)
}