package com.depromeet.knockknock.ui.alarmroomsearch

sealed class AlarmRoomSearchNavigationAction {
    object NavigateToHidePopularCategory: AlarmRoomSearchNavigationAction()
    class NavigateToRoom(val roomId: Int) : AlarmRoomSearchNavigationAction()
}