package com.depromeet.knockknock.ui.invitefriendtoroom

sealed class InviteFriendToRoomNavigationAction {
    class NavigateToCheckFriend(val userIdx: Int) : InviteFriendToRoomNavigationAction()
    object NavigateToInviteComplete : InviteFriendToRoomNavigationAction()
}