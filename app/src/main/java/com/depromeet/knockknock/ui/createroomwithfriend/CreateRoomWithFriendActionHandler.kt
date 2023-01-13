package com.depromeet.knockknock.ui.createroomwithfriend

interface CreateRoomWithFriendActionHandler {
    fun onInviteFriendClicked(userIdx: Int, isChecked : Boolean)
    fun onCompleteClicked()
}