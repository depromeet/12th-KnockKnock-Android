package com.depromeet.knockknock.ui.invitefriendtoroom

interface InviteFriendToRoomActionHandler {
    fun onInviteFriendClicked(userIdx: Int, isChecked : Boolean)
    fun onCompleteClicked()
}