package com.depromeet.knockknock.ui.friendlist

sealed class FriendListNavigationAction {
    object NavigateToLink: FriendListNavigationAction()
    class NavigateToAddFriend(val userIdx: Int) : FriendListNavigationAction()
    class NavigateToFriendMore(val userIdx: Int) : FriendListNavigationAction()
}