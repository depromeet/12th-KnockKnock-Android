package com.depromeet.knockknock.ui.aloneroominvitefriend

interface AloneRoomInviteFriendActionHandler {
    fun onInviteFriendClicked(userIdx: Int, isChecked : Boolean)
    fun onSkipCLicked()
    fun onCompleteClicked()
}