package com.depromeet.knockknock.ui.alarmroomjoined

interface AlarmRoomJoinedActionHandler {
    fun onAlarmRoomEditTextClicked()
    fun onRoomTypeAllClicked()
    fun onRoomTypeFriendClicked()
    fun onRoomTypeAloneClicked()
    fun onRoomClicked(roomId : Int)
}