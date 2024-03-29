package com.depromeet.knockknock.ui.home

interface HomeActionHandler {
    fun onCreatePushClicked()
    fun onRoomClicked(roomId: Int)
    fun onRecentAlarmClicked(alarmId: Int)
    fun onReactionClicked(alarmId: Int)
    fun onNotificationClicked()
    fun onRecentAlarmMoreClicked(alarmId: Int)
}