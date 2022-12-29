package com.depromeet.knockknock.ui.addfriend

sealed class AddFriendNavigationAction {
    class NavigateToAddFriend(val userIdx: Int, val nickname: String) : AddFriendNavigationAction()
    class NavigateToAddSuccess(val nickname: String) : AddFriendNavigationAction()
}