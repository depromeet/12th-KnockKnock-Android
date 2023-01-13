package com.depromeet.knockknock.ui.alarmroomsearch

sealed class AlarmRoomSearchNavigationAction {
    object NavigateToHidePopularCategory: AlarmRoomSearchNavigationAction()
    //class NavigateToMakeRoom(val roomName : String) : AlarmRoomSearchNavigationAction()
    object NavigateToMakeRoom : AlarmRoomSearchNavigationAction()
    class NavigateToRoom(val roomId: Int) : AlarmRoomSearchNavigationAction()
}