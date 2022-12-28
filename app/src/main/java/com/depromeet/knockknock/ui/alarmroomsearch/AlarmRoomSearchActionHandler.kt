package com.depromeet.knockknock.ui.alarmroomsearch

interface AlarmRoomSearchActionHandler {
    fun onAlarmRoomEditTextClicked()
    fun onAlarmRoomGenerateClicked(roomName : String)
    fun onRoomClicked(roomId : Int)
}