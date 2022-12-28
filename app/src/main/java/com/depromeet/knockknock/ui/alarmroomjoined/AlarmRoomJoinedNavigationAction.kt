package com.depromeet.knockknock.ui.alarmroomjoined

sealed class AlarmRoomJoinedNavigationAction {
    class NavigateToRoom(val roomId: Int) : AlarmRoomJoinedNavigationAction()
    object NavigateToAlarmRoomSearch : AlarmRoomJoinedNavigationAction()
}