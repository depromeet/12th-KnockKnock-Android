package com.depromeet.knockknock.ui.alarmroomsearch

interface AlarmRoomSearchActionHandler {
    fun onAlarmRoomEditTextClicked()
    //fun onAlarmRoomGenerateClicked(roomName : String)
    fun onAlarmRoomGenerateClicked()
    fun onRoomClicked(roomId : Int)
}