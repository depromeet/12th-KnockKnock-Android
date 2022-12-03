package com.depromeet.knockknock.ui.friendlist

interface FriendListActionHandler {
    fun onLinkedClicked()
    fun onFriendMoreClicked(userIdx: Int)
}