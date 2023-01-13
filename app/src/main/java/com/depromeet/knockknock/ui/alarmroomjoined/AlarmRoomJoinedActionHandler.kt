package com.depromeet.knockknock.ui.alarmroomjoined

interface AlarmRoomJoinedActionHandler {
    fun onRoomTypeAllClicked()
    fun onRoomTypeFriendClicked()
    fun onRoomTypeAloneClicked()
    fun onMakeRoomClicked()
    fun onRoomClicked(roomId : Int)
}