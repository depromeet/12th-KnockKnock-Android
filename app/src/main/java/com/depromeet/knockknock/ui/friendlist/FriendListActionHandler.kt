package com.depromeet.knockknock.ui.friendlist

interface FriendListActionHandler {
    fun onLinkedClicked()
    fun onAddFriendClicked(userIdx: Int)
    fun onFriendMoreClicked(userIdx: Int)
}