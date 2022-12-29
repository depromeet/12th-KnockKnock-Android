package com.depromeet.knockknock.ui.friendlist

sealed class FriendListNavigationAction {
    object NavigateToLink: FriendListNavigationAction()
    class NavigateToFriendMore(val userIdx: Int) : FriendListNavigationAction()
    object NavigateToDeleteSuccess: FriendListNavigationAction()
    object NavigateToDeclareSuccess: FriendListNavigationAction()
    object NavigateToAddFriends: FriendListNavigationAction()
}