package com.depromeet.knockknock.ui.alarmroomexplore

sealed class AlarmRoomExploreNavigationAction {
    object NavigateToAlarmRoomSearch: AlarmRoomExploreNavigationAction()
    class NavigateToMakeRoom(val roomName : String) : AlarmRoomExploreNavigationAction()
    class NavigateToRoom(val roomId: Int) : AlarmRoomExploreNavigationAction()
}