package com.depromeet.knockknock.ui.aloneroominvitefriend

sealed class AloneRoomInviteFriendNavigationAction {
    class NavigateToGeneratedRoom(val groupId : Int) : AloneRoomInviteFriendNavigationAction()
}