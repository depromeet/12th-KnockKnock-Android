package com.depromeet.knockknock.ui.addfriend

sealed class AddFriendNavigationAction {
    class NavigateToAddFriend(val userIdx: Int) : AddFriendNavigationAction()
}