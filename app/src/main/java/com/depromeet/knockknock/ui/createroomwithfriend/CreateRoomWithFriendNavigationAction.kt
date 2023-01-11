package com.depromeet.knockknock.ui.createroomwithfriend

sealed class CreateRoomWithFriendNavigationAction {
    class NavigateToCreateComplete(group_id: Int) : CreateRoomWithFriendNavigationAction()
}