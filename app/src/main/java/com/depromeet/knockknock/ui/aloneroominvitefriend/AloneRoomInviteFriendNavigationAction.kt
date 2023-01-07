package com.depromeet.knockknock.ui.aloneroominvitefriend

sealed class AloneRoomInviteFriendNavigationAction {
    class NavigateToGeneratedRoom(groupId : Int) : AloneRoomInviteFriendNavigationAction()
}